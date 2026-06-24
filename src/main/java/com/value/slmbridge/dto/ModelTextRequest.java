package com.value.slmbridge.dto;

import jakarta.validation.constraints.NotBlank;

public class ModelTextRequest {

    @NotBlank(message = "Text is required")
    private String text;

    private String model = "mistral";

    public ModelTextRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}