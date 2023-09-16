package org.saigon4paws.Controllers.Manager;

import jakarta.validation.Valid;
import org.saigon4paws.DTO.AdopterDTO;
import org.saigon4paws.DTO.AdopterDTO;
import org.saigon4paws.Models.Adopter;
import org.saigon4paws.Models.Adopter;
import org.saigon4paws.Services.AdopterService;
import org.saigon4paws.Services.PetService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/manager/adopter")
public class AdopterController {
    @Autowired
    private AdopterService adopterService;

    @Autowired
    private PetService petService;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String adopterIndex() {
        return "redirect:/manager/adopter";
    }

    @RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
    public String adopterIndex(@RequestParam(value = "page", required = false) Integer pageNo,
                           @RequestParam(value = "size", required = false) Integer pageSize,
                           Model model) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        Page<Adopter> adopterPage = adopterService.findAll(pageNo - 1, pageSize);
        model.addAttribute("page", adopterPage);
        model.addAttribute("adopters", adopterPage.getContent());
        return "manager/adopter/index";
    }

    @GetMapping("/add")
    public String adopterAdd(Model model) {
        List<Long> petIds = petService.getAllNonAdoptedPetId();
        model.addAttribute("petIds", petIds);
        model.addAttribute("adopterDTO", new AdopterDTO());
        return "manager/adopter/form";
    }

    @PostMapping("/add")
    public String adopterAdd(@ModelAttribute("adopterDTO") @Valid AdopterDTO adopterDTO,
                         BindingResult result,
                         Model model) {
        List<Long> petIds = petService.getAllNonAdoptedPetId();
        model.addAttribute("petIds", petIds);
        model.addAttribute("adopterDTO", adopterDTO);
        if (result.hasErrors()) {
            return "manager/adopter/form";
        }
        try {
            AdopterDTO savedAdopterDTO = adopterService.saveAdopter(adopterDTO);
            model.addAttribute("adopterDTO", savedAdopterDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/adopter/form";
        }
        model.addAttribute("success", "Adopter created successfully!");
        return "manager/adopter/form";
    }

    @GetMapping("/edit")
    public String adopterEdit(@RequestParam("id") Long id, Model model) {
        List<Long> petIds = petService.getAllNonAdoptedPetId();
        model.addAttribute("petIds", petIds);
        AdopterDTO existingAdopterDTO = adopterService.getAdopterDTOById(id);
        if (existingAdopterDTO == null) {
            model.addAttribute("error", "Adopter not found!");
            return "manager/adopter/form";
        }
        model.addAttribute("isEditPage", true);
        model.addAttribute("adopterDTO", existingAdopterDTO);
        return "manager/adopter/form";
    }

    @PostMapping("/edit")
    public String adopterEditSubmit(@ModelAttribute("adopterDTO") @Valid AdopterDTO adopterDTO,
                                BindingResult result,
                                Model model) {
        List<Long> petIds = petService.getAllNonAdoptedPetId();
        model.addAttribute("petIds", petIds);
        model.addAttribute("isEditPage", true);
        model.addAttribute("adopterDTO", adopterDTO);
        Adopter existingAdopter = adopterService.getAdopterById(adopterDTO.getId());
        if (existingAdopter == null) {
            model.addAttribute("error", "Adopter not found!");
            return "manager/adopter/form";
        }
        if (result.hasErrors()) {
            return "manager/adopter/form";
        }
        try {
            AdopterDTO updatedAdopterDTO = adopterService.updateAdopter(adopterDTO.getId(), adopterDTO);
            model.addAttribute("adopterDTO", updatedAdopterDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/adopter/form";
        }
        model.addAttribute("success", "Adopter updated successfully!");
        return "manager/adopter/form";
    }

    @PostMapping("/delete")
    public String adopterDelete(@RequestParam("id") Long id, Model model) {
        try {
            adopterService.deleteAdopter(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/adopter";
        }
        model.addAttribute("success", "Adopter deleted successfully!");
        return "go-back";
    }
}
