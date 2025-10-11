package com.example.aidocumentsearch.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PdfProcessingService {

    @Value("${app.chunk.size:1000}")
    private int chunkSize;

    @Value("${app.chunk.overlap:200}")
    private int chunkOverlap;

    public String extractTextFromPdf(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        try (PDDocument document = PDDocument.load(bytes)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public List<String> splitTextIntoChunks(String text) {
        List<String> chunks = new ArrayList<>();
        
        // Clean up the text
        text = cleanText(text);
        
        // Split by sentences first, then by paragraphs
        String[] sentences = text.split("[.!?]+\\s+");
        
        StringBuilder currentChunk = new StringBuilder();
        int currentLength = 0;
        
        for (String sentence : sentences) {
            sentence = sentence.trim();
            if (sentence.isEmpty()) continue;
            
            // If adding this sentence would exceed chunk size, save current chunk
            if (currentLength + sentence.length() > chunkSize && currentChunk.length() > 0) {
                chunks.add(currentChunk.toString().trim());
                
                // Start new chunk with overlap
                String overlap = getOverlapText(currentChunk.toString());
                currentChunk = new StringBuilder(overlap);
                currentLength = overlap.length();
            }
            
            currentChunk.append(sentence).append(". ");
            currentLength += sentence.length() + 2;
        }
        
        // Add the last chunk if it's not empty
        if (currentChunk.length() > 0) {
            chunks.add(currentChunk.toString().trim());
        }
        
        return chunks;
    }

    private String cleanText(String text) {
        // Remove excessive whitespace and normalize
        text = text.replaceAll("\\s+", " ");
        text = text.replaceAll("\n+", "\n");
        text = text.trim();
        
        // Remove common PDF artifacts
        text = text.replaceAll("\\f", ""); // Form feed characters
        text = text.replaceAll("\\r", ""); // Carriage returns
        
        return text;
    }

    private String getOverlapText(String chunk) {
        if (chunk.length() <= chunkOverlap) {
            return chunk;
        }
        
        // Get the last chunkOverlap characters, but try to break at word boundary
        String overlap = chunk.substring(chunk.length() - chunkOverlap);
        int lastSpaceIndex = overlap.indexOf(' ');
        
        if (lastSpaceIndex > 0) {
            return overlap.substring(lastSpaceIndex + 1);
        }
        
        return overlap;
    }

    public boolean isValidPdfFile(MultipartFile file) {
        return file != null && 
               !file.isEmpty() && 
               "application/pdf".equals(file.getContentType()) &&
               file.getOriginalFilename() != null &&
               file.getOriginalFilename().toLowerCase().endsWith(".pdf");
    }
}

