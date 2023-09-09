package org.saigon4paws.Controllers.Manager;

import jakarta.validation.Valid;
import org.saigon4paws.DTO.ReliefGroupDTO;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Services.ReliefGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager/relief-group")
public class ReliefGroupController {
    @Autowired
    private ReliefGroupService reliefGroupService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        model.addAttribute("reliefGroups", reliefGroups);
        return "manager/relief-group/index";
    }

    @GetMapping("/add")
    public String reliefGroupAdd(Model model) {
        ReliefGroupDTO reliefGroupDTO = new ReliefGroupDTO();
        model.addAttribute("reliefGroupDTO", reliefGroupDTO);
        return "manager/relief-group/form";
    }

    @PostMapping("/add")
    public String reliefGroupAddSubmit(@ModelAttribute("reliefGroupDTO") @Valid ReliefGroupDTO reliefGroupDTO,
                                       BindingResult result,
                                       Model model) {
        model.addAttribute("reliefGroupDTO", reliefGroupDTO);
        if (result.hasErrors()) {
            return "manager/relief-group/form";
        }
        try {
            reliefGroupService.createReliefGroup(reliefGroupDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/relief-group/form";
        }
        model.addAttribute("success", "Relief group created successfully!");
        return "manager/relief-group/form";
    }

    @GetMapping("/edit")
    public String reliefGroupEdit(@RequestParam("id") Integer id, Model model) {
        ReliefGroupDTO reliefGroupDTO = reliefGroupService.getReliefGroupById(id);
        if (reliefGroupDTO == null) {
            model.addAttribute("error", "Relief group not found!");
            return "manager/relief-group/form";
        }
        model.addAttribute("reliefGroupDTO", reliefGroupDTO);
        return "manager/relief-group/form";
    }

    @PostMapping("/edit")
    public String reliefGroupEditSubmit(
            @ModelAttribute("reliefGroupDTO") @Valid ReliefGroupDTO reliefGroupDTO,
            BindingResult result,
            Model model) {
        model.addAttribute("reliefGroupDTO", reliefGroupDTO);
        if (result.hasErrors()) {
            return "manager/relief-group/form";
        }
        ReliefGroupDTO updatedReliefGroupDTO;
        try {
            updatedReliefGroupDTO = reliefGroupService.updateReliefGroupById(reliefGroupDTO.getId(), reliefGroupDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/relief-group/form";
        }
        model.addAttribute("reliefGroupDTO", updatedReliefGroupDTO);
        model.addAttribute("success", "Relief group updated successfully!");
        return "manager/relief-group/form";
    }

    @PostMapping("/delete")
    public String reliefGroupDelete(@RequestParam("id") Integer id, Model model) {
        try {
            reliefGroupService.deleteReliefGroupById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/relief-group/";
        }
        return "redirect:/manager/relief-group/";
    }
}
