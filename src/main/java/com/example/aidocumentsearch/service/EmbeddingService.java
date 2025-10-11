package com.example.aidocumentsearch.service;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.data.embedding.Embedding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmbeddingService {

    @Autowired
    private EmbeddingModel embeddingModel;

    public List<Double> generateEmbedding(String text) {
        Embedding embedding = embeddingModel.embed(text).content();
        return embedding.vectorAsList().stream()
                .map(Float::doubleValue)
                .collect(Collectors.toList());
    }

    public List<List<Double>> generateEmbeddings(List<String> texts) {
        return texts.stream()
                .map(this::generateEmbedding)
                .collect(Collectors.toList());
    }
}

