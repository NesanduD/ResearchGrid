package com.university.ResearchGrid.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    // The Centralized API Caller
    private String callGeminiApi(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        String fullUrl = apiUrl + "?key=" + apiKey;

        Map<String, Object> part = Map.of("text", prompt);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> requestBody = Map.of("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(fullUrl, entity, Map.class);
            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            Map<String, Object> firstCandidate = candidates.get(0);
            Map<String, Object> responseContent = (Map<String, Object>) firstCandidate.get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) responseContent.get("parts");

            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            return "AI Generation Failed: " + e.getMessage();
        }
    }

    // Original Feature
    public String suggestMilestones(String title, String researchArea) {
        String prompt = "You are a senior research assistant. Suggest 3 concrete, professional milestones for a research project titled '"
                + title + "' in the field of '" + researchArea + "'. Return only the milestones as a simple list without extra conversational text.";
        return callGeminiApi(prompt);
    }

    // New Feature 1: Automated Paper Summarization
    public String summarizeAbstract(String abstractText) {
        String prompt = "You are an expert academic researcher. Summarize the following academic abstract in exactly 3 concise, easy-to-understand sentences. Abstract: " + abstractText;
        return callGeminiApi(prompt);
    }

    // New Feature 2: Domain Classification
    public String classifyDomain(String projectTitle, String description) {
        String prompt = "You are an AI research classifier. Classify the following research project into a specific, recognized academic domain (e.g., Quantum Computing, Bioinformatics, Cognitive Psychology). Return ONLY the domain name, nothing else. Title: " + projectTitle + ". Description: " + description;
        return callGeminiApi(prompt);
    }
}