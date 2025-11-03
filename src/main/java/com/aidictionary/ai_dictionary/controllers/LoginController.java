package com.aidictionary.ai_dictionary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// (Я добавил сюда код из вашего предыдущего вопроса для полноты)
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model,
            Principal principal

    ) {
        if (principal != null) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("errorMessage", "Nieprawidłowa nazwa użytkownika lub hasło");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Pomyślnie wylogowano.");
        }
        return "login";
    }

}