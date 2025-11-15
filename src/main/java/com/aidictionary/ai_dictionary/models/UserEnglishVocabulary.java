package com.aidictionary.ai_dictionary.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"user", "word"})
@Entity
@Table(name = "user_english_vocabulary")
public class UserEnglishVocabulary {

    @EmbeddedId
    private UserEnglishVocabularyId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("wordId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private EnglishWord word;

    @Column(name = "study_status")
    private String studyStatus;

    @Column(name = "last_reviewed_at")
    private LocalDateTime lastReviewedAt;

    @Column(name = "review_count")
    private int reviewCount;

    @Column(name = "correct_attempts")
    private int correctAttempts;

    @Column(name = "incorrect_attempts")
    private int incorrectAttempts;

    @Column(name = "added_at")
    private LocalDateTime addedAt;


}
