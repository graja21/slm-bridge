package com.value.slmbridge.client;

import com.value.slmbridge.dto.ModelTextRequest;
import com.value.slmbridge.dto.QuestionRequest;
import com.value.slmbridge.dto.TextRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public class FastApiClient {

    private final WebClient fastApiWebClient;

    public FastApiClient(WebClient fastApiWebClient) {
        this.fastApiWebClient = fastApiWebClient;
    }

    public Map<String, Object> classify(ModelTextRequest request) {
        return postJson("/classify", request);
    }

    public Map<String, Object> summarize(TextRequest request) {
        return postJson("/summarize", request);
    }

    public Map<String, Object> extract(TextRequest request) {
        return postJson("/extract", request);
    }

    public Map<String, Object> financialExtract(ModelTextRequest request) {
        return postJson("/financial-extract", request);
    }

    public Map<String, Object> askDocument(QuestionRequest request) {
        return postJson("/ask-document", request);
    }

    public Map<String, Object> ragStatus() {
        return fastApiWebClient.get()
                .uri("/rag-status")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private Map<String, Object> postJson(String uri, Object body) {
        return fastApiWebClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    public Map<String, Object> financialPdfChunked(MultipartFile file) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();

            builder.part("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            return fastApiWebClient.post()
                    .uri("/financial-pdf-chunked")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(builder.build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

        } catch (Exception e) {
            throw new RuntimeException("Failed to send PDF to FastAPI", e);
        }
    }

    public Map<String, Object> indexPdf(MultipartFile file) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();

            builder.part("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            return fastApiWebClient.post()
                    .uri("/index-pdf")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(builder.build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

        } catch (Exception e) {
            throw new RuntimeException("Failed to index PDF in FastAPI", e);
        }
    }
}