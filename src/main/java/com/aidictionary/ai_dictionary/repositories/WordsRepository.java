package com.aidictionary.ai_dictionary.repositories;


import com.aidictionary.ai_dictionary.models.Words;
import org.springframework.data.repository.CrudRepository;
public interface WordsRepository extends CrudRepository<Words, Long> {
    
}
