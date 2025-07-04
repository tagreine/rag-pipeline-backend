package com.example.rag.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Configuration
public class AzureBlobStorageConfig {
    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Value("${azure.storage.endpoint}")
    private String endpoint;

    @Bean
    public BlobServiceClient blobServiceClient() {
        String connectionString = String.format(
            "DefaultEndpointsProtocol=http;AccountName=%s;AccountKey=%s;BlobEndpoint=%s;",
            accountName, accountKey, endpoint
        );
        return new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
    }
}
