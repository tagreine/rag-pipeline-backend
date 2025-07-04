package com.example.rag.model;

import java.util.List;

public class ChunkDocument {
    private String id;
    private String documentName;
    private String chunkText;
    private List<Double> embedding;

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

    public List<Double> getEmbedding() {
        return embedding;
    }
    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }
}
