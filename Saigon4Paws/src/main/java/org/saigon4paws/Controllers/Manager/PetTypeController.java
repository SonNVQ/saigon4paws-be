package org.saigon4paws.Controllers.Manager;

import jakarta.validation.Valid;
import org.saigon4paws.DTO.PetTypeDTO;
import org.saigon4paws.Services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/pet-type")
public class PetTypeController {
    @Autowired
    private PetTypeService petTypeService;

    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String petTypeIndex(Model model) {
        model.addAttribute("petTypes", petTypeService.getAllPetTypes());
        return "manager/pet-type/index";
    }

    @GetMapping("/add")
    public String petTypeAdd(Model model) {
        PetTypeDTO petTypeDTO = new PetTypeDTO();
        model.addAttribute("petTypeDTO", petTypeDTO);
        return "manager/pet-type/form";
    }

    @PostMapping("/add")
    public String petTypeAddSubmit(@ModelAttribute("petTypeDTO") @Valid PetTypeDTO petTypeDTO,
                                      BindingResult result,
                                   Model model) {
        model.addAttribute("petTypeDTO", petTypeDTO);
        try {
            petTypeService.createPetType(petTypeDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/pet-type/form";
        }
        model.addAttribute("success", "Pet type created successfully!");
        return "manager/pet-type/form";
    }

    @GetMapping("/edit")
    public String petTypeEdit(@RequestParam("id") Integer id, Model model) {
        PetTypeDTO petTypeDTO = petTypeService.getPetTypeDTOById(id);
        if (petTypeDTO == null) {
            model.addAttribute("error", "Pet type not found!");
            return "manager/pet-type/form";
        }
        model.addAttribute("petTypeDTO", petTypeDTO);
        return "manager/pet-type/form";
    }

    @PostMapping("/edit")
    public String petTypeEditSubmit(@ModelAttribute("petTypeDTO") @Valid PetTypeDTO petTypeDTO,
                                    BindingResult result,
                                    Model model) {
        model.addAttribute("petTypeDTO", petTypeDTO);
        PetTypeDTO updatedPetTypeDTO;
        try {
            updatedPetTypeDTO = petTypeService.updatePetTypeDTOById(petTypeDTO.getId(), petTypeDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/pet-type/form";
        }
        model.addAttribute("petTypeDTO", updatedPetTypeDTO);
        model.addAttribute("success", "Pet type updated successfully!");
        return "manager/pet-type/form";
    }

    @PostMapping("/delete")
    public String petTypeDelete(@RequestParam("id") Integer id, Model model) {
        try {
            petTypeService.deletePetTypeById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/pet-type/";
        }
        model.addAttribute("success", "Pet type deleted successfully!");
        return "forward:/manager/pet-type/";
    }

}
