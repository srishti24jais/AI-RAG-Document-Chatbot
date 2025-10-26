package com.example.aidocumentsearch.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    @Autowired
    private ChatLanguageModel chatLanguageModel;

    @Autowired
    private QdrantVectorService vectorDatabaseService;

    @Autowired
    private EmbeddingService embeddingService;

    @Value("${app.top.k.results:5}")
    private int topKResults;

    public String generateAnswer(String question) {
        // Generate embedding for the question
        List<Double> questionEmbedding = embeddingService.generateEmbedding(question);

        // Search for relevant chunks
        List<String> relevantChunks = vectorDatabaseService.searchSimilarChunks(questionEmbedding, topKResults);

        if (relevantChunks.isEmpty()) {
            return "I couldn't find any relevant information in the uploaded documents to answer your question.";
        }

        // Create context from relevant chunks
        String context = relevantChunks.stream()
                .collect(Collectors.joining("\n\n"));

        // Create the prompt
        String prompt = String.format(
                "Based on the following context from uploaded documents, please answer the user's question.\n" +
                "If the answer cannot be found in the context, please say so.\n\n" +
                "Context:\n%s\n\nQuestion: %s\n\nAnswer:",
                context, question);

        // Debug logging
        System.out.println("=== RAG PROMPT DEBUG ===");
        System.out.println("Question: " + question);
        System.out.println("Context length: " + context.length() + " characters");
        System.out.println("Prompt length: " + prompt.length() + " characters");
        System.out.println("First 200 chars of context: " + context.substring(0, Math.min(200, context.length())));
        System.out.println("=== END DEBUG ===");

        // Generate response using the chat model
        UserMessage userMessage = UserMessage.from(prompt);
        Response<AiMessage> response = chatLanguageModel.generate(userMessage);
        AiMessage aiMessage = response.content();
        
        return aiMessage.text();
    }

    public List<String> getSources(String question) {
        List<Double> questionEmbedding = embeddingService.generateEmbedding(question);
        return vectorDatabaseService.searchSimilarChunks(questionEmbedding, topKResults);
    }
}

