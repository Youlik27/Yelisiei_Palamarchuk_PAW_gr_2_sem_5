package com.aidictionary.ai_dictionary.models;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public void setId(Short id) { this.id = id; }

    public void setName(String name) { this.name = name; }
}