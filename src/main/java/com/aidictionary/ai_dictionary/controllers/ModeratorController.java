package com.aidictionary.ai_dictionary.controllers;

import com.aidictionary.ai_dictionary.models.WordDefinition;
import com.aidictionary.ai_dictionary.repositories.WordDefinitionRepository;
import com.aidictionary.ai_dictionary.services.WordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    private final WordService wordService;
    private final WordDefinitionRepository wordDefinitionRepository;

    public ModeratorController(WordService wordService, WordDefinitionRepository wordDefinitionRepository) {
        this.wordService = wordService;
        this.wordDefinitionRepository = wordDefinitionRepository;
    }

    @GetMapping("/words")
    @Transactional(readOnly = true)
    public String listWords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String search,
            Model model) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<WordDefinition> wordPage = wordService.getDefinitionsForModerator(search, pageable);

        int totalPages = wordPage.getTotalPages();
        int startPage = Math.max(0, page - 2);
        int endPage = Math.min(totalPages - 1, page + 2);

        model.addAttribute("words", wordPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("search", search);

        return "moderateWords";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        WordDefinition word = wordDefinitionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("word", word);
        return "editWord";
    }

    @PostMapping("/edit/{id}")
    public String updateWord(@PathVariable Long id,
                             @ModelAttribute("word") WordDefinition word,
                             RedirectAttributes redirectAttributes) {
        wordService.updateDefinition(id, word);
        redirectAttributes.addFlashAttribute("successMessage", "Zmiany zapisane!");
        return "redirect:/moderator/words";
    }
}