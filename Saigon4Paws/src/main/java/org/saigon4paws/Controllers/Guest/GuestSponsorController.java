package org.saigon4paws.Controllers.Guest;

import jakarta.validation.Valid;
import org.saigon4paws.DTO.ReliefGroupDTO;
import org.saigon4paws.DTO.SponsorshipInformationDTO;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Services.BankService;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Services.SponsorshipInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sponsor")
public class GuestSponsorController {
    @Autowired
    private ReliefGroupService reliefGroupService;

    @Autowired
    private SponsorshipInformationService sponsorshipInformationService;

    @Autowired
    private BankService bankService;

    @GetMapping("")
    public String sponsorIndex(Model model) {
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        model.addAttribute("reliefGroups", reliefGroups);
        return "guest/sponsor/index";
    }

    @GetMapping("/relief-group")
    public String sponsorReliefGroup(Model model,
                                     @RequestParam(value = "id", required = true) Integer id) {
        ReliefGroupDTO reliefGroupDTO = reliefGroupService.getReliefGroupDTOById(id);
        model.addAttribute("reliefGroupDTO", reliefGroupDTO);

        SponsorshipInformationDTO sponsorshipInformationDTO = new SponsorshipInformationDTO();
        model.addAttribute("sponsorshipInformationDTO", sponsorshipInformationDTO);

        return "guest/sponsor/relief-group";
    }

    @PostMapping("/relief-group")
    public String sponsorReliefGroupSubmit(
            @RequestParam(value = "id", required = true) Integer id,
            @ModelAttribute("sponsorshipInformationDTO") @Valid SponsorshipInformationDTO sponsorshipInformationDTO,
            BindingResult result,
            Model model) {
        ReliefGroupDTO reliefGroupDTO = reliefGroupService.getReliefGroupDTOById(id);
        model.addAttribute("reliefGroupDTO", reliefGroupDTO);
        model.addAttribute("sponsorshipInformationDTO", sponsorshipInformationDTO);
        if (result.hasErrors()) {
            return "guest/sponsor/relief-group";
        }
        sponsorshipInformationDTO.setStatus("Chưa xử lý");
        sponsorshipInformationDTO.setReliefGroupId(id);
        SponsorshipInformationDTO savedSponsorshipInformationDTO;
        try {
            savedSponsorshipInformationDTO = sponsorshipInformationService.createSponsorshipInformation(sponsorshipInformationDTO);
            model.addAttribute("sponsorshipInformationDTO", savedSponsorshipInformationDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "guest/sponsor/relief-group";
        }
        return "redirect:/sponsor/payment-method?id=" + savedSponsorshipInformationDTO.getId().toString();
    }

    @GetMapping("/payment-method")
    public String sponsorMethod(@RequestParam("id") Long id, Model model) {
        SponsorshipInformationDTO sponsorshipInformationDTO = sponsorshipInformationService.getSponsorshipInformationDTOById(id);
        model.addAttribute("sponsorshipInformationDTO", sponsorshipInformationDTO);
        return "guest/sponsor/payment-method";
    }

    @PostMapping("/payment-method")
    public String sponsorSuccess(@RequestParam("sponsorshipInformationId") Long id, Model model) {
        SponsorshipInformationDTO sponsorshipInformationDTO = sponsorshipInformationService.getSponsorshipInformationDTOById(id);
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(sponsorshipInformationDTO.getReliefGroupId());
        String qrLink = bankService.getVietQRLink(
                reliefGroup.getBankBin(),
                reliefGroup.getBankAccountNumber(),
                reliefGroup.getBankAccountName(),
                sponsorshipInformationDTO.getAmount(),
                "Sponsorship id - " + sponsorshipInformationDTO.getId().toString()
        );
        model.addAttribute("qrLink", qrLink);
        return "guest/sponsor/success";
    }
}
