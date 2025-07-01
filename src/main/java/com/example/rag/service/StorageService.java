package com.example.rag.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadPdf(MultipartFile file) throws Exception;
    InputStream downloadPdf(String originalFilename) throws Exception;
}