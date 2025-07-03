package com.example.rag.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Service
public class AzureBlobStorageService implements StorageService {
    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Value("${azure.storage.endpoint}")
    private String endpoint;

    private final String containerName = "pdfs";

    @Override
    public String uploadPdf(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String connectionString = String.format(
            "DefaultEndpointsProtocol=http;AccountName=%s;AccountKey=%s;BlobEndpoint=%s;",
            accountName, accountKey, endpoint
        );
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(this.containerName);
        if (!containerClient.exists()) {
            containerClient.create();
        }

        BlobClient blobClient = containerClient.getBlobClient(originalFilename);
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();
    }

    @Override
    public InputStream downloadPdf(String filename) throws Exception {
        String connectionString = String.format(
            "DefaultEndpointsProtocol=http;AccountName=%s;AccountKey=%s;BlobEndpoint=%s;",
            accountName, accountKey, endpoint
        );
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(this.containerName);
        BlobClient blobClient = containerClient.getBlobClient(filename);

        return blobClient.openInputStream();
    }
}
