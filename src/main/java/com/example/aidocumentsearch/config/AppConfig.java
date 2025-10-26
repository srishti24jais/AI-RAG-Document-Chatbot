package com.example.aidocumentsearch.config;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import com.example.aidocumentsearch.service.GeminiHttpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

    @Value("${openai.api.key:}")
    private String openaiApiKey;
    
    @Value("${googleai.api.key:}")
    private String googleAiApiKey;

    @Bean
    @Primary
    public EmbeddingModel embeddingModel() {
        if (openaiApiKey != null && !openaiApiKey.trim().isEmpty()) {
            // Use OpenAI embeddings for better accuracy
            System.out.println("Using OpenAI text-embedding-3-small model");
            return OpenAiEmbeddingModel.builder()
                    .apiKey(openaiApiKey)
                    .modelName("text-embedding-3-small")
                    .build();
        } else {
            throw new IllegalStateException("OpenAI API key is required. Please set OPENAI_API_KEY in your .env file.");
        }
    }

    @Bean
    @Primary
    public ChatLanguageModel chatLanguageModel() {
        // Debug logging
        System.out.println("DEBUG: googleAiApiKey length: " + (googleAiApiKey != null ? googleAiApiKey.length() : "null"));
        System.out.println("DEBUG: openaiApiKey length: " + (openaiApiKey != null ? openaiApiKey.length() : "null"));
        
        // Check for Gemini Pro API key first
        if (googleAiApiKey != null && !googleAiApiKey.trim().isEmpty()) {
            System.out.println("Using Google Gemini Pro");
            return new GeminiHttpService();
        }
        
        // Fallback to OpenAI
        if (openaiApiKey != null && !openaiApiKey.trim().isEmpty()) {
            System.out.println("Using OpenAI GPT-3.5-turbo");
            return OpenAiChatModel.builder()
                    .apiKey(openaiApiKey)
                    .modelName("gpt-3.5-turbo")
                    .temperature(0.7)
                    .maxTokens(1000)
                    .build();
        }
        
        throw new IllegalStateException("Either GOOGLEAI_API_KEY or OPENAI_API_KEY is required in your .env file.");
    }
}

