package com.aidictionary.ai_dictionary.controllers;

import com.aidictionary.ai_dictionary.models.Words;
import com.aidictionary.ai_dictionary.repositories.WordsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final WordsRepository wordsRepository;

    public MainController(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Words> words = wordsRepository.findAll();
        model.addAttribute("words", words);
        return "home";
    }


}