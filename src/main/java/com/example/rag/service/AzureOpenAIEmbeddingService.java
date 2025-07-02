package com.example.rag.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AzureOpenAIEmbeddingService implements EmbeddingService {
    @Override
    public List<Float> embed(String text) {
        // TODO: Call Azure OpenAI embedding endpoint and return the embedding vector
        // For now, return a dummy vector
        return new ArrayList<>();
    }
}