package org.saigon4paws.Controllers.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/pet")
public class PetController {

    @GetMapping({"", "/"})
    public String petIndex(Model model) {
        return "manager/pet/index";
    }

    @GetMapping("/add")
    public String petAdd() {
        return "manager/pet/form";
    }

}
