package com.aidictionary.ai_dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AiDictionaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiDictionaryApplication.class, args);
    }

}
