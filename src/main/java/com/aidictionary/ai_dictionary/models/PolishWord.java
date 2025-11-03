package com.aidictionary.ai_dictionary.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "polish_words")
public class PolishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    @Getter
    private String word;
    @Getter
    @Setter
    private Integer frequency;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
