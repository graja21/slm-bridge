package com.value.slmbridge.dto;

import jakarta.validation.constraints.NotBlank;

public class TextRequest {

    @NotBlank(message = "Text is required")
    private String text;

    public TextRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}