package com.example.aichatbot;

public class OllamaRequest {

    public String model;
    public String prompt;
    public boolean stream;

    public OllamaRequest(String prompt) {
        this.model = "gemma3:1b-it-q4_K_M";
        this.prompt = prompt;
        this.stream = false;
    }
}