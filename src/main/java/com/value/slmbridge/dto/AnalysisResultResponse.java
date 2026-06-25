package com.value.slmbridge.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class AnalysisResultResponse {

    private String id;
    private String analysisType;
    private String model;
    private String inputText;
    private String filename;
    private Map<String, Object> result;
    private LocalDateTime createdAt;

    public AnalysisResultResponse() {
    }

    public AnalysisResultResponse(String id, String analysisType, String model, String inputText,
                                  String filename, Map<String, Object> result, LocalDateTime createdAt) {
        this.id = id;
        this.analysisType = analysisType;
        this.model = model;
        this.inputText = inputText;
        this.filename = filename;
        this.result = result;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getAnalysisType() { return analysisType; }
    public String getModel() { return model; }
    public String getInputText() { return inputText; }
    public String getFilename() { return filename; }
    public Map<String, Object> getResult() { return result; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(String id) { this.id = id; }
    public void setAnalysisType(String analysisType) { this.analysisType = analysisType; }
    public void setModel(String model) { this.model = model; }
    public void setInputText(String inputText) { this.inputText = inputText; }
    public void setFilename(String filename) { this.filename = filename; }
    public void setResult(Map<String, Object> result) { this.result = result; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}