package com.aidictionary.ai_dictionary.models;
import jakarta.persistence.*;

@Entity
@Table(name = "polish_words")
public class PolishWords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String word;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
}
