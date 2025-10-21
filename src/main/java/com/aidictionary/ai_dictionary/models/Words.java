package com.aidictionary.ai_dictionary.models;

import jakarta.persistence.*;

@Entity
@Table(name = "words")

public class Words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word_pl, word_en, description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

        public String getPolish() {
            return word_pl;
    }

    public void setPolish(String polish) {
        this.word_pl = polish;
    }

    public String getEnglish() {
        return word_en;
    }

    public void setEnglish(String english) {
        this.word_en = english;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
