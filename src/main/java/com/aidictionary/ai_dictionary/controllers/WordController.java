package com.aidictionary.ai_dictionary.controllers;

import com.aidictionary.ai_dictionary.models.EnglishWord;
import com.aidictionary.ai_dictionary.models.UserEnglishVocabulary;
import com.aidictionary.ai_dictionary.models.WordDefinition;
import com.aidictionary.ai_dictionary.services.WordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }


    @GetMapping("/search")
    @ResponseBody
    public Iterable<EnglishWord> searchWords(@RequestParam("q") String query) {
        return wordService.searchWords(query);
    }

    @GetMapping("/word/{wordName}")
    public String viewWordDetails(@PathVariable String wordName, Model model, Principal principal) {


        List<WordDefinition> words = wordService.getWordDetails(wordName);

        if (words.isEmpty()) {
            return "error/404";
        }
        model.addAttribute("wordTranslations", words);


        if (!words.isEmpty()) {
            EnglishWord wordObject = words.get(0).getEnglishWord();
            model.addAttribute("wordId", words.get(0).getEnglishWord().getId());
            model.addAttribute("wordObject",wordObject);
            UserEnglishVocabulary getWordStatus = wordService.getWordStatus(principal, wordObject.getId());
            model.addAttribute("getWordStatus",getWordStatus);
        }

        return "wordDetails";
    }

    @PostMapping("/word/status-change")
    public String changeWordStatus(
            Principal principal,
            @RequestParam("wordId") Long wordId,
            @RequestParam("status") String status,
            @RequestParam("wordName") String wordName)
    {

        wordService.changeWordStatus(principal, wordId, status);

        return "redirect:/word/" + wordName;
    }

}