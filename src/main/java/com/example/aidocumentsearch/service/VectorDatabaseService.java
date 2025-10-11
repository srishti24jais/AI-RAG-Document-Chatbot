package com.example.aidocumentsearch.service;

import java.util.List;

public interface VectorDatabaseService {
    
    /**
     * Store document chunks with their embeddings in the vector database
     * @param chunks List of text chunks
     * @param filename Original filename
     * @return Number of chunks stored
     */
    int storeDocumentChunks(List<String> chunks, String filename);
    
    /**
     * Search for similar chunks based on query embedding
     * @param queryEmbedding The embedding vector of the query
     * @param topK Number of top results to return
     * @return List of similar text chunks
     */
    List<String> searchSimilarChunks(List<Double> queryEmbedding, int topK);
    
    /**
     * Delete all documents (for testing/cleanup)
     */
    void deleteAllDocuments();
    
    /**
     * Check if the vector database is available
     * @return true if available, false otherwise
     */
    boolean isAvailable();
}

