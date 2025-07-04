package com.example.rag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.rag.service.StorageService;

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

    @GetMapping("/health/blob")
    public ResponseEntity<String> blobContainerExist() {
        if (this.storageService.blobServiceRunning()) {
            return ResponseEntity.ok("Blob service is running.");
        } else {
            return ResponseEntity.status(503).body("Blob service is not available.");
        }
    }

    @PostMapping("/ingest/pdf")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile pdfFile) {
        String originalFilename = pdfFile.getOriginalFilename();
        if (pdfFile.isEmpty() || originalFilename == null || !originalFilename.endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Please upload a PDF file.");
        }
        try {
            String blobUrl = this.storageService.uploadPdf(pdfFile);
            if (blobUrl == null) {
                return ResponseEntity.internalServerError().body("Failed to upload file to Azure Blob Storage.");
            }
            return ResponseEntity.ok("File uploaded successfully: " + blobUrl);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to upload or process file.");
        }
    }
}