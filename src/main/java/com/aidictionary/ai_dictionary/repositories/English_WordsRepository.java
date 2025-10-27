package com.aidictionary.ai_dictionary.repositories;

import com.aidictionary.ai_dictionary.models.EnglishWords;
import com.aidictionary.ai_dictionary.models.WordDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface English_WordsRepository extends JpaRepository<EnglishWords, Long> {
    List<EnglishWords> findByWordIgnoreCase(String english);
    Iterable<EnglishWords> findByWordContainingIgnoreCase(String word);

}
