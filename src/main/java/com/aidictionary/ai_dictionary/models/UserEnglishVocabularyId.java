package com.aidictionary.ai_dictionary.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserEnglishVocabularyId implements Serializable {

    private Long userId;
    private Long wordId;

    public UserEnglishVocabularyId() {}

    public UserEnglishVocabularyId(Long userId, Long wordId) {
        this.userId = userId;
        this.wordId = wordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEnglishVocabularyId that = (UserEnglishVocabularyId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(wordId, that.wordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, wordId);
    }
}
