package com.aidictionary.ai_dictionary.models;

import jakarta.persistence.*;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PolishWord getPolishWord() {
        return polishWord;
    }

    public void setPolishWord(PolishWord polishWord) {
        this.polishWord = polishWord;
    }

    public EnglishWord getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(EnglishWord englishWord) {
        this.englishWord = englishWord;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public short getSenseIndex() {
        return senseIndex;
    }

    public void setSenseIndex(short senseIndex) {
        this.senseIndex = senseIndex;
    }

    public short getTranslationIndex() {
        return translationIndex;
    }

    public void setTranslationIndex(short translationIndex) {
        this.translationIndex = translationIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRawGloss() {
        return rawGloss;
    }

    public void setRawGloss(String rawGloss) {
        this.rawGloss = rawGloss;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIpa() {
        return ipa;
    }

    public void setIpa(String ipa) {
        this.ipa = ipa;
    }
}