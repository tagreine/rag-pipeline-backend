package com.example.rag.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.rag.model.ChunkDocument;

@Repository
public interface ChunkRepository extends ElasticsearchRepository<ChunkDocument, String> {
    // You can add custom query methods here if needed
}