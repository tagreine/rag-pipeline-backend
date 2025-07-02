package com.example.rag.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Represents a chunk of text extracted from a document, along with its metadata.
 * This class is used to store the text chunks and their embeddings for retrieval and processing.
 */
@Document(indexName = "chunks")
public class ChunkDocument {
    @Id
    private String id;
    private String documentName;
    private String chunkText;
    private List<Float> embedding;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getChunkText() {
        return chunkText;
    }
    public void setChunkText(String chunkText) {
        this.chunkText = chunkText;
    }

    public List<Float> getEmbedding() {
        return embedding;
    }
    public void setEmbedding(List<Float> embedding) {
        this.embedding = embedding;
    }
}
