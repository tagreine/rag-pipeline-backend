package com.example.rag.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;

public interface StorageService {
    boolean blobServiceRunning();
    String uploadPdf(MultipartFile file) throws Exception;
    InputStream downloadPdf(String originalFilename) throws Exception;
    BlobClient getBlob(String containerName, String blob) throws Exception;
}