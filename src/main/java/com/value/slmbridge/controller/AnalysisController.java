package com.value.slmbridge.controller;

import com.value.slmbridge.dto.ModelTextRequest;
import com.value.slmbridge.dto.QuestionRequest;
import com.value.slmbridge.dto.TextRequest;
import com.value.slmbridge.entity.AnalysisResult;
import com.value.slmbridge.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "*")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/classify")
    public AnalysisResult classify(@Valid @RequestBody ModelTextRequest request) {
        return analysisService.classify(request);
    }

    @PostMapping("/summarize")
    public AnalysisResult summarize(@Valid @RequestBody TextRequest request) {
        return analysisService.summarize(request);
    }

    @PostMapping("/extract")
    public AnalysisResult extract(@Valid @RequestBody TextRequest request) {
        return analysisService.extract(request);
    }

    @PostMapping("/financial-extract")
    public AnalysisResult financialExtract(@Valid @RequestBody ModelTextRequest request) {
        return analysisService.financialExtract(request);
    }

    @GetMapping("/rag-status")
    public Map<String, Object> ragStatus() {
        return analysisService.ragStatus();
    }

    @PostMapping("/ask-document")
    public AnalysisResult askDocument(@Valid @RequestBody QuestionRequest request) {
        return analysisService.askDocument(request);
    }

    @GetMapping("/history")
    public List<AnalysisResult> history() {
        return analysisService.getHistory();
    }

    @GetMapping("/history/{type}")
    public List<AnalysisResult> historyByType(@PathVariable String type) {
        return analysisService.getHistoryByType(type);
    }

    @PostMapping(value = "/financial-pdf-chunked", consumes = "multipart/form-data")
    public AnalysisResult financialPdfChunked(@RequestPart("file") MultipartFile file) {
        return analysisService.financialPdfChunked(file);
    }

    @DeleteMapping("/history/{id}")
    public Map<String, String> deleteHistory(@PathVariable String id) {
        analysisService.deleteAnalysis(id);
        return Map.of("message", "Analysis deleted successfully");
    }
}