package com.value.slmbridge.service;

import com.value.slmbridge.client.FastApiClient;
import com.value.slmbridge.dto.ModelTextRequest;
import com.value.slmbridge.dto.QuestionRequest;
import com.value.slmbridge.dto.TextRequest;
import com.value.slmbridge.entity.AnalysisResult;
import com.value.slmbridge.repository.AnalysisResultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AnalysisService {

    private final FastApiClient fastApiClient;
    private final AnalysisResultRepository repository;

    public AnalysisService(
            FastApiClient fastApiClient,
            AnalysisResultRepository repository
    ) {
        this.fastApiClient = fastApiClient;
        this.repository = repository;
    }

    public AnalysisResult classify(ModelTextRequest request, String userEmail) {
        Map<String, Object> result = fastApiClient.classify(request);
        return save("classification", request.getModel(), request.getText(), null, userEmail, result);
    }

    public AnalysisResult summarize(TextRequest request, String userEmail) {
        Map<String, Object> result = fastApiClient.summarize(request);
        return save("summarization", "mistral", request.getText(), null, userEmail, result);
    }

    public AnalysisResult extract(TextRequest request, String userEmail) {
        Map<String, Object> result = fastApiClient.extract(request);
        return save("extraction", "mistral", request.getText(), null, userEmail, result);
    }

    public AnalysisResult financialExtract(ModelTextRequest request, String userEmail) {
        Map<String, Object> result = fastApiClient.financialExtract(request);
        return save("financial_extraction", request.getModel(), request.getText(), null, userEmail, result);
    }

    public AnalysisResult askDocument(QuestionRequest request, String userEmail) {
        Map<String, Object> result = fastApiClient.askDocument(request);
        return save("rag_qa", request.getModel(), request.getQuestion(), null, userEmail, result);
    }

    public AnalysisResult financialPdfChunked(MultipartFile file, String userEmail) {
        Map<String, Object> result = fastApiClient.financialPdfChunked(file);

        return save(
                "financial_pdf_chunked",
                "mistral",
                null,
                file.getOriginalFilename(),
                userEmail,
                result
        );
    }

    public Map<String, Object> ragStatus() {
        return fastApiClient.ragStatus();
    }

    public Page<AnalysisResult> getHistory(
            String userEmail,
            String analysisType,
            String model,
            Pageable pageable
    ) {
        if (analysisType != null && !analysisType.isBlank()
                && model != null && !model.isBlank()) {
            return repository.findByUserEmailAndAnalysisTypeAndModelOrderByCreatedAtDesc(
                    userEmail,
                    analysisType,
                    model,
                    pageable
            );
        }

        if (analysisType != null && !analysisType.isBlank()) {
            return repository.findByUserEmailAndAnalysisTypeOrderByCreatedAtDesc(
                    userEmail,
                    analysisType,
                    pageable
            );
        }

        if (model != null && !model.isBlank()) {
            return repository.findByUserEmailAndModelOrderByCreatedAtDesc(
                    userEmail,
                    model,
                    pageable
            );
        }

        return repository.findByUserEmailOrderByCreatedAtDesc(
                userEmail,
                pageable
        );
    }

    public void deleteAnalysis(String id) {
        repository.deleteById(id);
    }

    private AnalysisResult save(
            String analysisType,
            String model,
            String inputText,
            String filename,
            String userEmail,
            Map<String, Object> result
    ) {
        AnalysisResult analysisResult = new AnalysisResult();

        analysisResult.setAnalysisType(analysisType);
        analysisResult.setModel(model);
        analysisResult.setInputText(inputText);
        analysisResult.setFilename(filename);
        analysisResult.setUserEmail(userEmail);
        analysisResult.setResult(result);
        analysisResult.setCreatedAt(LocalDateTime.now());

        return repository.save(analysisResult);
    }
}