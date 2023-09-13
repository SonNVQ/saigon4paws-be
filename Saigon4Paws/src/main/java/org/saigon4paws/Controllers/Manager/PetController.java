package org.saigon4paws.Controllers.Manager;

import jakarta.validation.Valid;
import org.saigon4paws.DTO.PetDTO;
import org.saigon4paws.Models.Pet;
import org.saigon4paws.Services.PetService;
import org.saigon4paws.Services.PetTypeService;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/manager/pet")
public class PetController {
    @Autowired
    private PetService petService;

    @Autowired
    private PetTypeService petTypeService;

    @Autowired
    private ReliefGroupService reliefGroupService;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String petIndex() {
        return "redirect:/manager/pet";
    }

    @RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
    public String petIndex(@RequestParam(value = "page", required = false) Integer pageNo,
                           @RequestParam(value = "size", required = false) Integer pageSize,
                           Model model) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        Page<Pet> petPage = petService.findAll(pageNo - 1, pageSize);
        model.addAttribute("page", petPage);
        model.addAttribute("currentPage", petPage.getNumber());
        model.addAttribute("totalPages", petPage.getTotalPages());
        model.addAttribute("totalItems", petPage.getTotalElements());
        model.addAttribute("pets", petPage.getContent());
        return "manager/pet/index";
    }

    @GetMapping("/add")
    public String petAdd(Model model) {
        model.addAttribute("petDTO", new PetDTO());
        model.addAttribute("petTypes", petTypeService.getAllPetTypes());
        model.addAttribute("reliefGroups", reliefGroupService.getAllReliefGroups());
        return "manager/pet/form";
    }

    @PostMapping("/add")
    public String petAdd(@ModelAttribute("petDTO") @Valid PetDTO petDTO,
                         @RequestParam("avatar") MultipartFile avatar,
                         BindingResult result,
                         Model model) {
        model.addAttribute("petDTO", petDTO);
        model.addAttribute("petTypes", petTypeService.getAllPetTypes());
        model.addAttribute("reliefGroups", reliefGroupService.getAllReliefGroups());
        if (result.hasErrors()) {
            return "manager/pet/form";
        }
        try {
            String savedPhotoUrl = petService.uploadPetPhoto(avatar);
            petDTO.setPhotoUrl(savedPhotoUrl);
            petService.createPet(petDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/pet/form";
        }
        model.addAttribute("success", "Pet created successfully!");
        return "manager/pet/form";
    }

    @GetMapping("/edit")
    public String petEdit(@RequestParam("id") Long id, Model model) {
        PetDTO existingPetDTO = petService.getPetDTOById(id);
        if (existingPetDTO == null) {
            model.addAttribute("error", "Pet not found!");
            return "manager/pet/form";
        }
        model.addAttribute("isEditPage", true);
        model.addAttribute("petDTO", existingPetDTO);
        model.addAttribute("petTypes", petTypeService.getAllPetTypes());
        model.addAttribute("reliefGroups", reliefGroupService.getAllReliefGroups());
        return "manager/pet/form";
    }

    @PostMapping("/edit")
    public String petEditSubmit(@ModelAttribute("petDTO") @Valid PetDTO petDTO,
                                @RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                BindingResult result,
                                Model model) {
        model.addAttribute("isEditPage", true);
        model.addAttribute("petDTO", petDTO);
        model.addAttribute("petTypes", petTypeService.getAllPetTypes());
        model.addAttribute("reliefGroups", reliefGroupService.getAllReliefGroups());
        Pet existingPet = petService.getPetById(petDTO.getId());
        if (existingPet == null) {
            model.addAttribute("error", "Pet not found!");
            return "manager/pet/form";
        }
        if (result.hasErrors()) {
            return "manager/pet/form";
        }
        try {
            String savedPhotoUrl = petService.uploadPetPhoto(avatar);
            petDTO.setPhotoUrl(savedPhotoUrl);
            PetDTO updatedPetDTO = petService.updatePet(petDTO.getId(), petDTO);
            model.addAttribute("petDTO", updatedPetDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/pet/form";
        }
        model.addAttribute("success", "Pet updated successfully!");
        return "manager/pet/form";
    }

    @PostMapping("/delete")
    public String petDelete(@RequestParam("id") Long id, Model model) {
        try {
            petService.deletePet(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/pet";
        }
        model.addAttribute("success", "Pet deleted successfully!");
        return "forward:/manager/pet";
    }

}
