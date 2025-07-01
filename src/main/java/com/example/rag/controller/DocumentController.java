package com.example.rag.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class DocumentController {
    @GetMapping("/hello")
    public String hello() {
        System.out.println("Hello from RAG backend!");
        return "Hello from RAG backend!";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (file.isEmpty() || originalFilename == null || !originalFilename.endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Please upload a PDF file.");
        }
        try {
            // Save to a temp directory for now
            File tempFile = File.createTempFile("upload-", ".pdf");
            file.transferTo(tempFile);
            System.out.println("File saved to: " + tempFile.getAbsolutePath());
            return ResponseEntity.ok("File uploaded successfully: " + tempFile.getName());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload file.");
        }
    }
}
