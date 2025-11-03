package com.aidictionary.ai_dictionary.repositories;

import com.aidictionary.ai_dictionary.models.EnglishWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface English_WordsRepository extends JpaRepository<EnglishWord, Long> {
    List<EnglishWord> findByWordIgnoreCase(String english);
    Iterable<EnglishWord> findByWordContainingIgnoreCase(String word);

}
