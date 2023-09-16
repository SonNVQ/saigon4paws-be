package org.saigon4paws.Controllers.Guest;

import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Services.ReliefGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class GuestHomepageController {
    @Autowired
    private ReliefGroupService reliefGroupService;

    @GetMapping("")
    public String index(Model model) {
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        model.addAttribute("reliefGroups", reliefGroups);
        return "guest/homepage/homepage";
    }

    @GetMapping("/about")
    public String about(Model model) {
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        model.addAttribute("reliefGroups", reliefGroups);
        return "guest/homepage/about";
    }
}
