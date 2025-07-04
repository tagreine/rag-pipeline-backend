package com.example.rag.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;

@Service
public class AzureBlobStorageService implements StorageService {

    private final BlobServiceClient blobServiceClient;

    private final String pdfContainer = "pdfs";

    public AzureBlobStorageService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    @Override
    public boolean blobServiceRunning() {
        return this.blobServiceClient.getAccountInfo() != null;
    }

    @Override
    public BlobClient getBlob(String containerName, String blob) throws Exception {
        return this.getContainerClient(containerName).getBlobClient(blob);
    }

    @Override
    public String uploadPdf(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();

        BlobClient blobClient = this.getContainerClient(this.pdfContainer).getBlobClient(originalFilename);
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();
    }

    @Override
    public InputStream downloadPdf(String filename) throws Exception {
        BlobClient blobClient = this.getBlob(this.pdfContainer, filename);
        return blobClient.openInputStream();
    }

    private BlobContainerClient getContainerClient(String containerName) throws Exception {
        BlobContainerClient containerClient = this.blobServiceClient.getBlobContainerClient(containerName);
        if (!containerClient.exists()) {
            containerClient.create();
        }
        return containerClient;
    }
}
