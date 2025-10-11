package com.example.aidocumentsearch.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChatRequest {
    
    @NotBlank(message = "Question cannot be blank")
    @Size(max = 1000, message = "Question cannot exceed 1000 characters")
    private String question;
    
    public ChatRequest() {}
    
    public ChatRequest(String question) {
        this.question = question;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
}

