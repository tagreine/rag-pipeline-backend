package com.example.rag.controller;

import java.io.InputStream;
import java.util.List;

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

import com.example.rag.service.StorageService;
import com.example.rag.utils.RAGTextUtils;

@RestController
@RequestMapping("/api")
public class DocumentController {
    private final StorageService storageService;

    @Autowired
    public DocumentController(StorageService storageService) {
        this.storageService = storageService;
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
        String blobUrl = storageService.uploadPdf(file);
        if (blobUrl == null || blobUrl.isEmpty()) {
            return ResponseEntity.internalServerError().body("Failed to upload file.");
        }

        // Download and extract text
        try (InputStream pdfStream = storageService.downloadPdf(originalFilename);
             PDDocument document = PDDocument.load(pdfStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String extractedText = pdfStripper.getText(document);
            
            List<String> chunks = RAGTextUtils.chunkText(extractedText, 100);
            return ResponseEntity.ok("File uploaded. Extracted " + chunks.size() + " chunks. First chunk: " +
                (chunks.isEmpty() ? "" : chunks.get(0)));
        }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to upload or process file.");
        }
    }
}