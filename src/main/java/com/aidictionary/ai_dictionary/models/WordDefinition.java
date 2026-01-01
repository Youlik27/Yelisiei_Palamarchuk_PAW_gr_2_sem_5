package com.aidictionary.ai_dictionary.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "word_definitions")
public class WordDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "polish_word_id")
    private PolishWord polishWord;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "english_word_id")
    private EnglishWord englishWord;

    @Column(name = "lang_code")
    private String langCode;

    @Column(name = "pos")
    private String pos;

    @Column(name = "sense_index")
    private short senseIndex;

    @Column(name = "translation_index")
    private short translationIndex;

    @Column(name = "description")
    private String description;

    @Column(name = "raw_gloss")
    private String rawGloss;

    @Column(name = "tags")
    private String tags;

    @Column(name = "ipa")
    private String ipa;



}