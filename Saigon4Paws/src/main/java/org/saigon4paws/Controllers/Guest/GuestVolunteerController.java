package org.saigon4paws.Controllers.Guest;

import org.saigon4paws.DTO.VolunteerDTO;
import org.saigon4paws.Models.Pet;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/volunteer")
public class GuestVolunteerController {
    @Autowired
    private ReliefGroupService reliefGroupService;

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("")
    public String volunteerIndex() {
        return "guest/volunteer/index";
    }

    @GetMapping("/cleaner")
    public String volunteerCleaner() {
        return "guest/volunteer/cleaner";
    }

    @GetMapping("/carrier")
    public String volunteerCarrier() {
        return "guest/volunteer/carrier";
    }

    @GetMapping("/form")
    public String volunteerForm(Model model) {
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        model.addAttribute("reliefGroups", reliefGroups);

        VolunteerDTO volunteerDTO = new VolunteerDTO();
        model.addAttribute("volunteerDTO", volunteerDTO);
        return "guest/volunteer/volunteer-form";
    }

    @PostMapping("/form")
    public String volunteerFormSubmit(@ModelAttribute("volunteerDTO") VolunteerDTO volunteerDTO,
                                      BindingResult result,
                                      Model model) {
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        model.addAttribute("reliefGroups", reliefGroups);

        volunteerDTO.setStatus("Tạo mới");

        if (result.hasErrors()) {
            model.addAttribute("volunteerDTO", volunteerDTO);
            model.addAttribute("error", "Your volunteer form has not been submitted successfully!");
            return "guest/volunteer/volunteer-form";
        }

        VolunteerDTO savedVolunteerDTO = volunteerService.saveVolunteer(volunteerDTO);
        if (savedVolunteerDTO == null) {
            model.addAttribute("volunteerDTO", volunteerDTO);
            model.addAttribute("error", "Your volunteer form has not been submitted successfully!");
            return "guest/volunteer/volunteer-form";
        }

        return "redirect:/volunteer/success";
    }

    @GetMapping("/success")
    public String volunteerSuccess() {
        return "guest/volunteer/volunteer-success";
    }
}
