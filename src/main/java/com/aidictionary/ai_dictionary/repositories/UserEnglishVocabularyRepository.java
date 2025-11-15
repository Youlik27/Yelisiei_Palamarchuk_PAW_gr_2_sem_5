package com.aidictionary.ai_dictionary.repositories;

import com.aidictionary.ai_dictionary.models.UserEnglishVocabulary;
import com.aidictionary.ai_dictionary.models.UserEnglishVocabularyId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEnglishVocabularyRepository extends JpaRepository<UserEnglishVocabulary, UserEnglishVocabularyId> {
     Optional<UserEnglishVocabulary> findByUserIdAndWordId(Long userId, long wordId);
     List<UserEnglishVocabulary> findByUserId(long wordId);
     long countByUserIdAndStudyStatus(Long userId, String studyStatus);
}
