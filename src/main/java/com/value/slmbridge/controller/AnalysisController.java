package com.value.slmbridge.controller;

import com.value.slmbridge.dto.ModelTextRequest;
import com.value.slmbridge.dto.QuestionRequest;
import com.value.slmbridge.dto.TextRequest;
import com.value.slmbridge.entity.AnalysisResult;
import com.value.slmbridge.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "*")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/classify")
    public AnalysisResult classify(
            @Valid @RequestBody ModelTextRequest request,
            Authentication authentication
    ) {
        return analysisService.classify(request, authentication.getName());
    }

    @PostMapping("/summarize")
    public AnalysisResult summarize(
            @Valid @RequestBody TextRequest request,
            Authentication authentication
    ) {
        return analysisService.summarize(request, authentication.getName());
    }

    @PostMapping("/extract")
    public AnalysisResult extract(
            @Valid @RequestBody TextRequest request,
            Authentication authentication
    ) {
        return analysisService.extract(request, authentication.getName());
    }

    @PostMapping("/financial-extract")
    public AnalysisResult financialExtract(
            @Valid @RequestBody ModelTextRequest request,
            Authentication authentication
    ) {
        return analysisService.financialExtract(request, authentication.getName());
    }

    @PostMapping("/ask-document")
    public AnalysisResult askDocument(
            @Valid @RequestBody QuestionRequest request,
            Authentication authentication
    ) {
        return analysisService.askDocument(request, authentication.getName());
    }

    @PostMapping(value = "/financial-pdf-chunked", consumes = "multipart/form-data")
    public AnalysisResult financialPdfChunked(
            @RequestPart("file") MultipartFile file,
            Authentication authentication
    ) {
        return analysisService.financialPdfChunked(file, authentication.getName());
    }

    @GetMapping("/rag-status")
    public Map<String, Object> ragStatus() {
        return analysisService.ragStatus();
    }

    @GetMapping("/history")
    public Page<AnalysisResult> history(
            Authentication authentication,
            @RequestParam(required = false) String analysisType,
            @RequestParam(required = false) String model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return analysisService.getHistory(
                authentication.getName(),
                analysisType,
                model,
                pageable
        );
    }

    @DeleteMapping("/history/{id}")
    public Map<String, String> deleteHistory(@PathVariable String id) {
        analysisService.deleteAnalysis(id);

        return Map.of(
                "message",
                "Analysis deleted successfully"
        );
    }
}