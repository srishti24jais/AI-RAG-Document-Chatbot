package com.example.aidocumentsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QdrantVectorService {

    @Value("${qdrant.host:localhost}")
    private String qdrantHost;

    @Value("${qdrant.port:6333}")
    private int qdrantPort;

    @Value("${qdrant.collection.name:documents}")
    private String collectionName;

    @Autowired
    private EmbeddingService embeddingService;

    // In-memory storage for demo purposes
    // In production, this would connect to actual Qdrant instance
    private final Map<String, List<Double>> vectorStorage = new HashMap<>();
    private final Map<String, Map<String, Object>> metadataStorage = new HashMap<>();

    public int storeDocumentChunks(List<String> chunks, String filename) {
        int storedCount = 0;
        
        for (int i = 0; i < chunks.size(); i++) {
            String chunk = chunks.get(i);
            String vectorId = filename + "_chunk_" + i;
            
            // Generate embedding
            List<Double> embedding = embeddingService.generateEmbedding(chunk);
            
            // Store vector and metadata
            vectorStorage.put(vectorId, embedding);
            
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("filename", filename);
            metadata.put("chunk_index", i);
            metadata.put("text", chunk);
            metadataStorage.put(vectorId, metadata);
            
            storedCount++;
        }
        
        return storedCount;
    }

    public List<String> searchSimilarChunks(List<Double> queryEmbedding, int topK) {
        // Simple cosine similarity search
        List<SimilarityResult> results = new ArrayList<>();
        
        for (Map.Entry<String, List<Double>> entry : vectorStorage.entrySet()) {
            String vectorId = entry.getKey();
            List<Double> vector = entry.getValue();
            
            double similarity = cosineSimilarity(queryEmbedding, vector);
            results.add(new SimilarityResult(vectorId, similarity));
        }
        
        // Sort by similarity and get top K
        results.sort((a, b) -> Double.compare(b.similarity, a.similarity));
        
        List<String> topChunks = new ArrayList<>();
        for (int i = 0; i < Math.min(topK, results.size()); i++) {
            String vectorId = results.get(i).vectorId;
            Map<String, Object> metadata = metadataStorage.get(vectorId);
            if (metadata != null) {
                topChunks.add((String) metadata.get("text"));
            }
        }
        
        return topChunks;
    }

    public void deleteAllDocuments() {
        vectorStorage.clear();
        metadataStorage.clear();
    }

    public boolean isAvailable() {
        // For demo purposes, always return true
        // In production, this would check Qdrant connection
        return true;
    }

    private double cosineSimilarity(List<Double> a, List<Double> b) {
        if (a.size() != b.size()) {
            return 0.0;
        }
        
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        
        for (int i = 0; i < a.size(); i++) {
            dotProduct += a.get(i) * b.get(i);
            normA += a.get(i) * a.get(i);
            normB += b.get(i) * b.get(i);
        }
        
        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }
        
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private static class SimilarityResult {
        final String vectorId;
        final double similarity;
        
        SimilarityResult(String vectorId, double similarity) {
            this.vectorId = vectorId;
            this.similarity = similarity;
        }
    }
}


