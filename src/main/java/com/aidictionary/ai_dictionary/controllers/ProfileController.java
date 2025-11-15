package com.aidictionary.ai_dictionary.controllers;


import com.aidictionary.ai_dictionary.models.User;
import com.aidictionary.ai_dictionary.models.UserEnglishVocabulary;
import com.aidictionary.ai_dictionary.models.WordDefinition;
import com.aidictionary.ai_dictionary.repositories.UserRepository;
import com.aidictionary.ai_dictionary.services.ProfileService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller

public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    public ProfileController(ProfileService profileService, UserRepository userRepository) {
        this.profileService = profileService;
        this.userRepository = userRepository;
    }
    @GetMapping("/profile")
    public String showProfile(Principal principal, Model model) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Long userId = user.getId();
        ProfileService.Stats stats = profileService.calculateStats(userId);
        model.addAttribute("user", user);
        model.addAttribute("stats", stats);
        return "profile";
    }

    @GetMapping("/vocabluary")
    public String VocabluaryView(Principal principal, Model model) {
        String username = principal.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/";
        }
        User user = userOpt.get();
        Long userId = user.getId();
        List<UserEnglishVocabulary> allWords = profileService.getUserVocabulary(userId);        model.addAttribute("nieZnamWords", allWords.stream()
                .filter(v -> "NIE_ZNAM".equals(v.getStudyStatus()))
                .toList());
        model.addAttribute("uczeSieWords", allWords.stream()
                .filter(v -> "UCZE_SIE".equals(v.getStudyStatus()))
                .toList());
        model.addAttribute("znamWords", allWords.stream()
                .filter(v -> "ZNAM".equals(v.getStudyStatus()))
                .toList());
        model.addAttribute("wazneWords", allWords.stream()
                .filter(v -> "WAZNE".equals(v.getStudyStatus()))
                .toList());
        return "userVocabulary";
    }
}
