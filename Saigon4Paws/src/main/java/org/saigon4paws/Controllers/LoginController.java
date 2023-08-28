package org.saigon4paws.Controllers;

import jakarta.validation.Valid;
import org.saigon4paws.DTO.UserDTO;
import org.saigon4paws.Models.User;
import org.saigon4paws.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("user") UserDTO userDTO,
            BindingResult result,
            Model model
    ) {
        User existingUser = userService.findUserByUsername(userDTO.getUsername());
        if (existingUser == null)
            existingUser = userService.findUserByEmail(userDTO.getUsername());

        if (existingUser != null)
            result.rejectValue("email", null, "User already registered!");

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "/registration";
        }

        userService.saveUser(userDTO);
        return "redirect:/registration?success";
    }
}
