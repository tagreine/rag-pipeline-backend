
package com.example.rag.config;

import org.springframework.ai.azure.openai.AzureOpenAiEmbeddingClient;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;

@Configuration
public class AzureOpenAiConfig {

    @Value("${spring.ai.azure.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.azure.openai.endpoint}")
    private String endpoint;
    
    @Bean
    public OpenAIClient openAIClient() {
        return new OpenAIClientBuilder()
                    .credential(new AzureKeyCredential(apiKey))
                    .endpoint(endpoint)
                    .buildClient();
    }

    @Bean
    public EmbeddingClient embeddingClient(OpenAIClient openAIClient) {
        return new AzureOpenAiEmbeddingClient(openAIClient);
    }
}
