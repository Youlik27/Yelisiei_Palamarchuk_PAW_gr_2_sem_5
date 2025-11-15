package com.aidictionary.ai_dictionary.services;

import com.aidictionary.ai_dictionary.models.UserEnglishVocabulary;
import com.aidictionary.ai_dictionary.models.WordDefinition;
import com.aidictionary.ai_dictionary.repositories.UserEnglishVocabularyRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service

public class ProfileService {
    private final UserEnglishVocabularyRepository userEnglishVocabularyRepository;

    public ProfileService(UserEnglishVocabularyRepository userEnglishVocabularyRepository) {
        this.userEnglishVocabularyRepository = userEnglishVocabularyRepository;
    }


    public List<UserEnglishVocabulary>getUserVocabulary(Long userId){
        return userEnglishVocabularyRepository.findByUserId(userId);
    }
    public record Stats(
            long known,
            long learning,
            long important,
            long unknown
    ) {
    }
    public Stats calculateStats(Long userId){
        long known = userEnglishVocabularyRepository.countByUserIdAndStudyStatus(userId, "ZNAM");
        long learning = userEnglishVocabularyRepository.countByUserIdAndStudyStatus(userId, "UCZE_SIE");
        long important = userEnglishVocabularyRepository.countByUserIdAndStudyStatus(userId, "WAZNE");
        long unknown = userEnglishVocabularyRepository.countByUserIdAndStudyStatus(userId, "NIE_ZNAM");
        return new Stats(known, learning, important, unknown);
    }

}
