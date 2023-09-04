package org.saigon4paws.Controllers.Manager;

import org.saigon4paws.Models.Pet;
import org.saigon4paws.Services.PetService;
import org.saigon4paws.Services.PetTypeService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/manager/pet")
public class PetController {
    @Autowired
    private PetService petService;

    @Autowired
    private PetTypeService petTypeService;

    @GetMapping("/")
    public String petIndex() {
        return "redirect:/manager/pet";
    }

    @GetMapping({""})
    public String petIndex(@RequestParam(value = "page", required = false) Integer pageNo,
                           @RequestParam(value = "size", required = false) Integer pageSize,
                           Model model) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        Page<Pet> petPage = petService.findAll(pageNo - 1, pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", petPage.getTotalPages());
        model.addAttribute("totalItems", petPage.getTotalElements());
        model.addAttribute("pets", petPage.getContent());
        return "manager/pet/index";
    }

    @GetMapping("/add")
    public String petAdd(Model model) {
        model.addAttribute("petTypes", petTypeService.getAllPetTypes());
        return "manager/pet/form";
    }

}
