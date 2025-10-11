package com.example.aidocumentsearch.dto;

public class UploadResponse {
    
    private String message;
    private String filename;
    private int chunksCreated;
    private boolean success;
    
    public UploadResponse() {}
    
    public UploadResponse(String message, String filename, int chunksCreated, boolean success) {
        this.message = message;
        this.filename = filename;
        this.chunksCreated = chunksCreated;
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public int getChunksCreated() {
        return chunksCreated;
    }
    
    public void setChunksCreated(int chunksCreated) {
        this.chunksCreated = chunksCreated;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}

