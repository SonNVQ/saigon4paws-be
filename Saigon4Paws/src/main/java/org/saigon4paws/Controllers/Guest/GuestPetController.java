package org.saigon4paws.Controllers.Guest;

import org.saigon4paws.DTO.AdopterDTO;
import org.saigon4paws.Models.Pet;
import org.saigon4paws.Services.AdopterService;
import org.saigon4paws.Services.PetService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pet")
public class GuestPetController {
    @Autowired
    private PetService petService;

    @Autowired
    private AdopterService adopterService;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String petIndex() {
        return "redirect:/pet";
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
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", petPage.getTotalPages());
        model.addAttribute("totalItems", petPage.getTotalElements());
        model.addAttribute("pets", petPage.getContent());
        return "guest/pet/index";
    }

    @RequestMapping(value = {"/detail"}, method = {RequestMethod.GET})
    public String petDetail(@RequestParam(value = "id", required = true) Long id, Model model) {
        Pet pet = petService.getPetById(id);
        model.addAttribute("pet", pet);
        return "guest/pet/pet-info";
    }

    @GetMapping("/adopt")
    public String petAdopt(@RequestParam(value="pet-id", required = true) Long id, Model model) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return "redirect:/pet";
        }
        AdopterDTO adopterDTO = new AdopterDTO();
        model.addAttribute("adopterDTO", adopterDTO);
        model.addAttribute("petId", pet.getId());
        return "guest/pet/adoption-form";
    }

    @PostMapping("/adopt")
    public String petAdopt(@ModelAttribute("adopterDTO") AdopterDTO adopterDTO,
                           @RequestParam(value = "petId", required = true) Long petId,
                           Model model) {
        Pet pet = petService.getPetById(petId);
        if (pet == null) {
            return "redirect:/pet";
        }
        adopterDTO.setStatus("Tạo mới");
        AdopterDTO savedAdopterDTO = adopterService.saveAdopter(adopterDTO);
        if (savedAdopterDTO == null) {
            model.addAttribute("adopterDTO", adopterDTO);
            model.addAttribute("petId", pet.getId());
            model.addAttribute("error", "Your adoption form has not been submitted successfully!");
            return "guest/pet/adoption-form";
        }
        return "redirect:/pet/adopt/success";
    }

    @GetMapping("/adopt/success")
    public String petAdoptSuccess() {
        return "guest/pet/adoption-success";
    }
}
