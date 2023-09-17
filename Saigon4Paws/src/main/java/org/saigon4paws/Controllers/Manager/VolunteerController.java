package org.saigon4paws.Controllers.Manager;

import org.saigon4paws.DTO.VolunteerDTO;
import org.saigon4paws.Models.Volunteer;
import org.saigon4paws.Services.VolunteerService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager/volunteer")
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String petIndex() {
        return "redirect:/manager/volunteer";
    }

    @RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
    public String volunteerIndex(@RequestParam(value = "page", required = false) Integer pageNo,
                           @RequestParam(value = "size", required = false) Integer pageSize,
                           Model model) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        Page<Volunteer> volunteerPage = volunteerService.findAll(pageNo - 1, pageSize);
        model.addAttribute("page", volunteerPage);
        model.addAttribute("volunteers", volunteerPage.getContent());
        return "manager/volunteer/index";
    }

    @PostMapping("/delete")
    public String deleteVolunteer(@RequestParam(value = "id") Long id, Model model) {
        try {
            volunteerService.deleteVolunteer(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/volunteer";
        }
        model.addAttribute("success", "Volunteer deleted successfully!");
        return "forward:/manager/volunteer";
    }

    @PostMapping("/checked")
    public String checkedVolunteer(@RequestParam(value = "id") Long id, Model model) {
        VolunteerDTO volunteerDTO = volunteerService.getVolunteerDTOById(id);

        if (volunteerDTO == null) {
            model.addAttribute("error", "Volunteer information not found!");
            return "forward:/manager/volunteer";
        }

        volunteerDTO.setStatus("Đã duyệt");
        try {
            volunteerService.updateVolunteer(id, volunteerDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/volunteer";
        }
        return "go-back";
    }
}
