package com.example.aidocumentsearch.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class GeminiHttpService implements ChatLanguageModel {
    
    @Value("${googleai.api.key:}")
    private String googleAiApiKey;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public Response<AiMessage> generate(List<ChatMessage> messages) {
        try {
            // Get the last user message
            String userText = "";
            for (int i = messages.size() - 1; i >= 0; i--) {
                if (messages.get(i) instanceof UserMessage) {
                    userText = ((UserMessage) messages.get(i)).text();
                    break;
                }
            }
            
            if (userText.isEmpty()) {
                return Response.from(AiMessage.from("I didn't receive a valid message. Please try again."));
            }
            
            // Prepare the request to Gemini Pro API
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + googleAiApiKey;
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> content = new HashMap<>();
            Map<String, Object> part = new HashMap<>();
            part.put("text", userText);
            content.put("parts", List.of(part));
            requestBody.put("contents", List.of(content));
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
                
                if (candidates != null && !candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    Map<String, Object> contentResponse = (Map<String, Object>) candidate.get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) contentResponse.get("parts");
                    
                    if (parts != null && !parts.isEmpty()) {
                        String aiResponse = (String) parts.get(0).get("text");
                        return Response.from(AiMessage.from(aiResponse));
                    }
                }
            }
            
            return Response.from(AiMessage.from("I'm sorry, I couldn't generate a response. Please try again."));
            
        } catch (Exception e) {
            System.err.println("Error calling Gemini Pro: " + e.getMessage());
            return Response.from(AiMessage.from("I'm sorry, I encountered an error while processing your request. Please try again."));
        }
    }
}

