package com.example.aidocumentsearch.controller;

import com.example.aidocumentsearch.dto.UploadResponse;
import com.example.aidocumentsearch.service.PdfProcessingService;
import com.example.aidocumentsearch.service.VectorDatabaseService;
import com.example.aidocumentsearch.service.EmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    @Autowired
    private PdfProcessingService pdfProcessingService;

    @Autowired
    private VectorDatabaseService vectorDatabaseService;

    @Autowired
    private EmbeddingService embeddingService;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file
            if (!pdfProcessingService.isValidPdfFile(file)) {
                return ResponseEntity.badRequest()
                        .body(new UploadResponse("Invalid PDF file", null, 0, false));
            }

            // Extract text from PDF
            String text = pdfProcessingService.extractTextFromPdf(file);
            
            if (text.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new UploadResponse("PDF contains no readable text", file.getOriginalFilename(), 0, false));
            }

            // Split text into chunks
            List<String> chunks = pdfProcessingService.splitTextIntoChunks(text);
            
            // Generate embeddings for chunks
            List<List<Double>> embeddings = embeddingService.generateEmbeddings(chunks);
            
            // Store in vector database
            int storedChunks = vectorDatabaseService.storeDocumentChunks(chunks, file.getOriginalFilename());

            return ResponseEntity.ok(new UploadResponse(
                    "Document uploaded and processed successfully",
                    file.getOriginalFilename(),
                    storedChunks,
                    true
            ));

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(new UploadResponse("Error processing PDF: " + e.getMessage(), 
                            file.getOriginalFilename(), 0, false));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new UploadResponse("Unexpected error: " + e.getMessage(), 
                            file.getOriginalFilename(), 0, false));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        boolean isAvailable = vectorDatabaseService.isAvailable();
        if (isAvailable) {
            return ResponseEntity.ok("Vector database is available");
        } else {
            return ResponseEntity.status(503).body("Vector database is not available");
        }
    }
}

