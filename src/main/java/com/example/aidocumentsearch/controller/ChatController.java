package com.example.aidocumentsearch.controller;

import com.example.aidocumentsearch.dto.ChatRequest;
import com.example.aidocumentsearch.dto.ChatResponse;
import com.example.aidocumentsearch.service.RagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private RagService ragService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        try {
            String answer = ragService.generateAnswer(request.getQuestion());
            List<String> sources = ragService.getSources(request.getQuestion());
            
            // Create citations from sources (first 100 chars of each source)
            List<String> citations = sources.stream()
                    .map(source -> source.length() > 100 ? source.substring(0, 100) + "..." : source)
                    .collect(Collectors.toList());

            ChatResponse response = new ChatResponse(answer, sources, citations);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ChatResponse errorResponse = new ChatResponse(
                    "Sorry, I encountered an error while processing your question: " + e.getMessage(),
                    List.of(),
                    List.of()
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Chat API is working!");
    }
}

