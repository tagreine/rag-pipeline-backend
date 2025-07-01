package com.example.rag.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DocumentController {
    @GetMapping("/hello")
    public String hello() {
        System.out.println("Hello from RAG backend!");
        return "Hello from RAG backend!";
    }
}
