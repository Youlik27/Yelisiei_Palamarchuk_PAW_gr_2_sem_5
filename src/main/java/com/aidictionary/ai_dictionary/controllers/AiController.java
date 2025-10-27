package com.aidictionary.ai_dictionary.controllers;

import com.aidictionary.ai_dictionary.models.WordDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.aidictionary.ai_dictionary.repositories.WordDefinitionRepository;

@Controller
public class AiController {
    @Autowired
    private WordDefinitionRepository vocabularyRepository;


    public String greeting(Model model) {
        model.addAttribute("name", "Użytkowniku");
        Iterable<WordDefinition> vocabulary = vocabularyRepository.findAll();
        model.addAttribute("vocabulary", vocabulary);
        return "ai";
    }

    @PostMapping("/query")
    public String queryOllama(@RequestParam("prompt") String prompt, Model model) {
        model.addAttribute("name", "Użytkowniku");

        String url = "http://localhost:11434/api/generate";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String formattedPrompt = "Ty jesteś profesjonalnym słownikiem polsko-angielskim.\n" +
                "Twoim zadaniem jest tworzenie słownika:\n" +
                "1. Jeśli użytkownik poda temat/sytuację (np. 'podróż do Włoch'), wygeneruj listę słów/wyrażeń przydatnych w tej sytuacji. Dla każdego słowa podaj:\n" +
                "   - Polish: <słowo>\n" +
                "   - English: <angielski odpowiednik>\n" +
                "   - IPA: <transkrypcja wymowy>\n" +
                "   - Examples: podawaj 2-3 zdania po angielsku, a pod każdym zdaniem jego **dokładny tłumaczenie na polski z zachowaniem osoby, osoby mówiącej i liczby**. Każde zdanie w osobnej linii, w kolejności: angielski → polski → następne angielski → polski.\n" +
                "2. Jeśli użytkownik poda **pojedyncze słowo**, podaj tłumaczenie, IPA i 2-3 przykłady w tej samej kolejności: angielski → polski (z zachowaniem osoby) → angielski → polski itd.\n" +
                "3. Nie używaj tabel, kategorii ani dodatkowych opisów.\n\n" +
                "Wejście użytkownika: \"" + prompt + "\"\n" +
                "Odpowiedź:";




        GenerateRequest requestBody = new GenerateRequest("deepseek-v3.1:671b-cloud", formattedPrompt, false);
        HttpEntity<GenerateRequest> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<GenerateResponse> response = restTemplate.postForEntity(url, entity, GenerateResponse.class);
            if (response.getBody() != null) {
                model.addAttribute("response", response.getBody().response);
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