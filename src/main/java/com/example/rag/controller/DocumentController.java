package com.example.rag.controller;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.rag.model.ChunkDocument;
import com.example.rag.repository.ChunkRepository;
import com.example.rag.service.EmbeddingService;
import com.example.rag.service.StorageService;
import com.example.rag.util.RAGTextUtils;

@RestController
@RequestMapping("/api")
public class DocumentController {
    private final StorageService storageService;
    private final EmbeddingService embeddingService;
    private final ChunkRepository chunkRepository;

    @Autowired
    public DocumentController(StorageService storageService, EmbeddingService embeddingService, ChunkRepository chunkRepository) {
        this.storageService = storageService;
        this.embeddingService = embeddingService;
        this.chunkRepository = chunkRepository;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from RAG backend!";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (file.isEmpty() || originalFilename == null || !originalFilename.endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Please upload a PDF file.");
        }
        try {
        String blobUrl = this.storageService.uploadPdf(file);
        if (blobUrl == null || blobUrl.isEmpty()) {
            return ResponseEntity.internalServerError().body("Failed to upload file.");
        }

        try (
            InputStream pdfStream = this.storageService.downloadPdf(originalFilename);
            PDDocument document = PDDocument.load(pdfStream);
            ) {
            
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String extractedText = pdfStripper.getText(document);

            List<String> chunks = RAGTextUtils.chunkText(extractedText, 500);
            int count = 0;
            for (String chunk : chunks) {
                
                List<Float> embedding = this.embeddingService.embed(chunk);
                
                ChunkDocument doc = new ChunkDocument();
                doc.setId(UUID.randomUUID().toString());
                doc.setDocumentName(originalFilename);
                doc.setChunkText(chunk);
                doc.setEmbedding(embedding);

                this.chunkRepository.save(doc);
                count++;
            }
            return ResponseEntity.ok("File uploaded. Indexed " + count + " chunks.");
        }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to upload or process file.");
        }
    }
}