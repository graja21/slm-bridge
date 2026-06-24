package com.value.slmbridge.dto;

import jakarta.validation.constraints.NotBlank;

public class QuestionRequest {

    @NotBlank(message = "Question is required")
    private String question;

    private String model = "mistral";

    public QuestionRequest() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}