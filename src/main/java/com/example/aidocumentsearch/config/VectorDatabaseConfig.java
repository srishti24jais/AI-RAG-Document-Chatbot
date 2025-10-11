package com.example.aidocumentsearch.config;

import com.example.aidocumentsearch.service.VectorDatabaseService;
import com.example.aidocumentsearch.service.QdrantVectorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class VectorDatabaseConfig {

    @Value("${vector.db:QDRANT}")
    private String vectorDbType;

    @Bean
    @Primary
    public VectorDatabaseService vectorDatabaseService(QdrantVectorService qdrantService) {
        return qdrantService; // Always use Qdrant since Pinecone is removed
    }
}

