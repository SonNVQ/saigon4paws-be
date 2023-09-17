package org.saigon4paws.Controllers.Manager;

import org.saigon4paws.DTO.SponsorshipInformationDTO;
import org.saigon4paws.Models.Pet;
import org.saigon4paws.Models.SponsorshipInformation;
import org.saigon4paws.Services.SponsorshipInformationService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/manager/sponsor")
public class SponsorController {
    @Autowired
    private SponsorshipInformationService sponsorshipInformationService;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String petIndex() {
        return "redirect:/manager/sponsor";
    }

    @RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
    public String sponsorIndex(@RequestParam(value = "page", required = false) Integer pageNo,
                           @RequestParam(value = "size", required = false) Integer pageSize,
                           Model model) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        Page<SponsorshipInformation> sponsorshipInformationPage = sponsorshipInformationService.findAll(pageNo - 1, pageSize);
        model.addAttribute("page", sponsorshipInformationPage);
        model.addAttribute("sponsorshipInformationList", sponsorshipInformationPage.getContent());
        return "manager/sponsor/index";
    }

    @PostMapping("/delete")
    public String deleteSponsor(@RequestParam(value = "id") Long id, Model model) {
        try {
            sponsorshipInformationService.deleteSponsorshipInformation(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/sponsor";
        }
        model.addAttribute("success", "Sponsorship information deleted successfully!");
        return "forward:/manager/sponsor";
    }

    @PostMapping("/checked")
    public String checkedSponsor(@RequestParam(value = "id") Long id, Model model) {
        SponsorshipInformationDTO sponsorshipInformationDTO = sponsorshipInformationService.getSponsorshipInformationDTOById(id);

        if (sponsorshipInformationDTO == null) {
            model.addAttribute("error", "Sponsorship information not found!");
            return "forward:/manager/sponsor";
        }
        sponsorshipInformationDTO.setStatus("Hoàn thành");
        try {
            sponsorshipInformationService.updateSponsorshipInformation(sponsorshipInformationDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/sponsor";
        }
        return "go-back";
    }
}
