package com.aidictionary.ai_dictionary.repositories;

import com.aidictionary.ai_dictionary.models.WordDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordDefinitionRepository extends JpaRepository<WordDefinition, Long> {
    List<WordDefinition> findByPolishWord_WordIgnoreCaseOrEnglishWord_WordIgnoreCase(String polishWordText, String englishWordText);
    List<WordDefinition> findByPolishWord_WordIgnoreCase(String polishWordText);
    List<WordDefinition> findByEnglishWord_WordIgnoreCase(String englishWordText);
    Iterable<WordDefinition> findByPolishWord_WordContainingIgnoreCaseOrEnglishWord_WordContainingIgnoreCase(String polishWordText, String englishWordText);
    List<WordDefinition> findByEnglishWord_WordIgnoreCaseAndPolishWord_FrequencyGreaterThanOrderByPolishWord_FrequencyDesc(String word, int frequency);
}