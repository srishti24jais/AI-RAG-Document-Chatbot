package com.example.aidocumentsearch.dto;

import java.util.List;

public class ChatResponse {
    
    private String answer;
    private List<String> sources;
    private List<String> citations;
    
    public ChatResponse() {}
    
    public ChatResponse(String answer, List<String> sources, List<String> citations) {
        this.answer = answer;
        this.sources = sources;
        this.citations = citations;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public List<String> getSources() {
        return sources;
    }
    
    public void setSources(List<String> sources) {
        this.sources = sources;
    }
    
    public List<String> getCitations() {
        return citations;
    }
    
    public void setCitations(List<String> citations) {
        this.citations = citations;
    }
}

