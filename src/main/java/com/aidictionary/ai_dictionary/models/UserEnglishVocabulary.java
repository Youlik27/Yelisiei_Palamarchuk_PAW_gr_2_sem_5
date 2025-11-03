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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private EnglishWord word;

    private String studyStatus;
    private LocalDateTime lastReviewedAt;
    private int reviewCount;
    private int correctAttempts;
    private int incorrectAttempts;
    private LocalDateTime addedAt;

}