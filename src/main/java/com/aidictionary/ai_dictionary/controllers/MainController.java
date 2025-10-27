package com.aidictionary.ai_dictionary.controllers;

import com.aidictionary.ai_dictionary.models.EnglishWords;
import com.aidictionary.ai_dictionary.models.WordDefinition;
import com.aidictionary.ai_dictionary.models.WordDefinition;
import com.aidictionary.ai_dictionary.repositories.English_WordsRepository;
import com.aidictionary.ai_dictionary.repositories.WordDefinitionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private final WordDefinitionRepository wordsRepository;
    private final English_WordsRepository english_WordsRepository;

    public MainController(WordDefinitionRepository wordsRepository, English_WordsRepository english_WordsRepository) {
        this.wordsRepository = wordsRepository;
        this.english_WordsRepository = english_WordsRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        Iterable<WordDefinition> words = wordsRepository.findAll();
        model.addAttribute("words", words);
        return "home";
    }
    @GetMapping("/search")
    @ResponseBody
    public Iterable<EnglishWords> searchWords(@RequestParam("q") String query) {
        if (query.trim().length() < 1) {
            return java.util.Collections.emptyList();
        }
        List<EnglishWords> exactMatches = english_WordsRepository.findByWordIgnoreCase(query);

        List<EnglishWords> partialMatches = (List<EnglishWords>) english_WordsRepository.findByWordContainingIgnoreCase(query);

        partialMatches.removeAll(exactMatches);

        List<EnglishWords> allResults = new ArrayList<>();
        allResults.addAll(exactMatches);
        allResults.addAll(partialMatches);
        if (allResults.size() > 6) {
            allResults = allResults.subList(0, 6);
        }

        return allResults;
    }
    @GetMapping("/{wordName}-{id}")
    public String viewWordDetails(
            @PathVariable String wordName,
            @PathVariable Long id,
            Model model
    ){
        List<WordDefinition> words = (List<WordDefinition>) wordsRepository.findByEnglishWord_WordIgnoreCase(wordName);

        if (words.isEmpty()) {
            return "error/404";
        }
        model.addAttribute("wordTranslations", words);
        model.addAttribute("word", wordName);
        return "wordDetails";
    }

}