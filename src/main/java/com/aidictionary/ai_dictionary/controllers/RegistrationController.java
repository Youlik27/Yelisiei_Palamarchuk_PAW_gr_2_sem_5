package com.aidictionary.ai_dictionary.controllers;

import com.aidictionary.ai_dictionary.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String repeatPassword,
                               RedirectAttributes redirectAttributes) {
        try {
            userService.registerNewUser(username, password, repeatPassword, email);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Rejestracja udana! Możesz się teraz zalogować.");
        return "redirect:/login";
    }
}