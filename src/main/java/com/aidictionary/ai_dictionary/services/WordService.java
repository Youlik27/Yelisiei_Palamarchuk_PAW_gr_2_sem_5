package com.aidictionary.ai_dictionary.services; // (рекомендую создать пакет services)

import com.aidictionary.ai_dictionary.models.*;
import com.aidictionary.ai_dictionary.repositories.English_WordsRepository;
import com.aidictionary.ai_dictionary.repositories.UserEnglishVocabularyRepository;
import com.aidictionary.ai_dictionary.repositories.UserRepository;
import com.aidictionary.ai_dictionary.repositories.WordDefinitionRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WordService {

    private final WordDefinitionRepository wordsRepository;
    private final English_WordsRepository english_WordsRepository;
    private final UserEnglishVocabularyRepository userEnglishVocabularyRepository;
    private final UserRepository userRepository;

    public WordService(WordDefinitionRepository wordsRepository,
                       English_WordsRepository englishWordsRepository, UserEnglishVocabularyRepository userEnglishVocabularyRepository, UserRepository userRepository) {
        this.wordsRepository = wordsRepository;
        this.english_WordsRepository = englishWordsRepository;
        this.userEnglishVocabularyRepository = userEnglishVocabularyRepository;
        this.userRepository = userRepository;
    }

    public List<EnglishWord> searchWords(String query) {
        if (query.trim().length() < 1) {
            return Collections.emptyList();
        }

        List<EnglishWord> exactMatches = english_WordsRepository.findByWordIgnoreCase(query);
        List<EnglishWord> partialMatches = (List<EnglishWord>) english_WordsRepository.findByWordContainingIgnoreCase(query);
        partialMatches.removeAll(exactMatches);

        List<EnglishWord> allResults = new ArrayList<>();
        allResults.addAll(exactMatches);
        allResults.addAll(partialMatches);

        if (allResults.size() > 6) {
            allResults = allResults.subList(0, 6);
        }
        return allResults;
    }

    public List<WordDefinition> getWordDetails(String wordName) {
        List<WordDefinition> words = (List<WordDefinition>) wordsRepository.findByEnglishWord_WordIgnoreCaseAndPolishWord_FrequencyGreaterThanOrderByPolishWord_FrequencyDesc(wordName, 0);

        if (words.isEmpty()) {
            words = wordsRepository.findByEnglishWord_WordIgnoreCase(wordName);
            if(words.isEmpty()) {
                return Collections.emptyList();
            }
        }
        return words;
    }
    public UserEnglishVocabulary getWordStatus(Principal principal, Long wordId) {
        if (principal == null) {
            return null;
        }
        String username = principal.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        Long userId = user.getId();

        return userEnglishVocabularyRepository.findByUserIdAndWordId(userId, wordId)
                .orElse(null);
    }
    public void changeWordStatus(Principal principal, Long wordId, String status) {
        String username = principal.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return;
        }

        User user = userOpt.get();
        Long userId = user.getId();

        Optional<UserEnglishVocabulary> userSavedWordOpt = userEnglishVocabularyRepository.findByUserIdAndWordId(userId, wordId);
        if (userSavedWordOpt.isPresent()) {
            UserEnglishVocabulary savedWord = userSavedWordOpt.get();
            savedWord.setStudyStatus(status);
            userEnglishVocabularyRepository.save(savedWord);

        } else {

            Optional<EnglishWord> wordOpt = english_WordsRepository.findById(wordId);
            if (wordOpt.isEmpty()) {
                return;
            }

            EnglishWord englishWord = wordOpt.get();
            UserEnglishVocabulary newEntry = new UserEnglishVocabulary();
            newEntry.setUser(user);
            newEntry.setWord(englishWord);
            newEntry.setStudyStatus(status);
            newEntry.setReviewCount(0);
            newEntry.setCorrectAttempts(0);
            newEntry.setIncorrectAttempts(0);
            UserEnglishVocabularyId vocabId = new UserEnglishVocabularyId(userId, wordId);
            newEntry.setId(vocabId);
            userEnglishVocabularyRepository.save(newEntry);
        }
    }
}