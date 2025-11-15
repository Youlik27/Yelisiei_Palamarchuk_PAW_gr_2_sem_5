package com.aidictionary.ai_dictionary.controllers;

import com.aidictionary.ai_dictionary.models.WordDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.aidictionary.ai_dictionary.repositories.WordDefinitionRepository;
import java.util.Collections;
@Controller
public class AiController {
    @Autowired
    private WordDefinitionRepository vocabularyRepository;
    @GetMapping("/ai")
    public String greeting(Model model) {
        return "ai";
    }

    @PostMapping("/query")
    public String queryOllama(@RequestParam("prompt") String prompt, Model model) {
        model.addAttribute("name", "Użytkowniku");

        String url = "http://localhost:11434/api/generate";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.ALL));

        String formattedPrompt = "Ty jesteś profesjonalnym słownikiem polsko-angielskim.\n" +
                "Twoim zadaniem jest tworzenie słownika, tłumacząc podane przez użytkownika polskie słowa lub zdania.\n\n" +
                "Instrukcje dotyczące odpowiedzi:\n" +
                "1.  Wszystkie tłumaczone słowa, frazy oraz przykładowe zdania (przykłady użycia) muszą być podane **wyłącznie w języku angielskim**.\n" +
                "2.  Możesz używać języka polskiego do tworzenia opisów, etykiet lub wstępów (na przykład: \"Tłumaczenie:\", \"Przykłady użycia:\", \"Oto Twoje słowa:\").\n" +
                "3.  Nie dodawaj żadnych polskich wyjaśnień do samych angielskich słów.\n\n" +
                "Wejście użytkownika: \"" + prompt + "\"\n" +
                "Odpowiedź:";

        GenerateRequest requestBody = new GenerateRequest("deepseek-v3.1:671b-cloud", formattedPrompt, false);
        HttpEntity<GenerateRequest> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            String body = response.getBody();

            if (body != null) {
                String clean = body
                        .replaceAll("\\\\n", "\n")
                        .replaceAll("[{}\\[\\]\"]", "")
                        .replaceAll("response:", "")
                        .replaceAll("model:.*", "")
                        .trim();

                String linked = clean.replaceAll("(?<!\\w)([A-Za-zÀ-ž]+)(?!\\w)", "<a href='/word/$1' class='word-link'>$1</a>");

                model.addAttribute("response", linked);
            } else {
                model.addAttribute("response", "Pusta odpowiedź od Ollama");
            }


        } catch (RestClientException e) {
            model.addAttribute("response", "Błąd zapytania do Ollama: " + e.getMessage());
        }

        return "ai";
    }


    static class GenerateRequest {
        public String model;
        public String prompt;
        public boolean stream;

        public GenerateRequest(String model, String prompt, boolean stream) {
            this.model = model;
            this.prompt = prompt;
            this.stream = stream;
        }
    }

    static class GenerateResponse {
        public String model;
        public String created_at;
        public String response;
        public boolean done;
    }

}