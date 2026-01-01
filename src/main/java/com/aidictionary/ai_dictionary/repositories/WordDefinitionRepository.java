package com.aidictionary.ai_dictionary.repositories;

import com.aidictionary.ai_dictionary.models.WordDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordDefinitionRepository extends JpaRepository<WordDefinition, Long> {
    List<WordDefinition> findByPolishWord_WordIgnoreCaseOrEnglishWord_WordIgnoreCase(String polishWordText, String englishWordText);
    List<WordDefinition> findByPolishWord_WordIgnoreCase(String polishWordText);
    List<WordDefinition> findByEnglishWord_WordIgnoreCase(String englishWordText);
    Iterable<WordDefinition> findByPolishWord_WordContainingIgnoreCaseOrEnglishWord_WordContainingIgnoreCase(String polishWordText, String englishWordText);
    List<WordDefinition> findByEnglishWord_WordIgnoreCaseAndPolishWord_FrequencyGreaterThanOrderByPolishWord_FrequencyDesc(String word, int frequency);
    @Query("SELECT wd FROM WordDefinition wd " +
            "WHERE LOWER(wd.englishWord.word) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND LENGTH(wd.englishWord.word) BETWEEN 3 AND 12 " +
            "ORDER BY " +
            "CASE WHEN LOWER(wd.englishWord.word) = LOWER(:query) THEN 0 ELSE 1 END ASC, " +
            "wd.polishWord.frequency DESC, " +
            "wd.englishWord.word ASC")
    Page<WordDefinition> findByWordWithPriority(@Param("query") String query, Pageable pageable);

    @EntityGraph(attributePaths = {"englishWord", "polishWord"})
    @Query("SELECT wd FROM WordDefinition wd " +
            "WHERE LENGTH(wd.englishWord.word) BETWEEN 3 AND 12")
    Page<WordDefinition> findAllWithLengthFilter(Pageable pageable);
}