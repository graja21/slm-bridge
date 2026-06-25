package com.value.slmbridge.repository;

import com.value.slmbridge.entity.AnalysisResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnalysisResultRepository extends MongoRepository<AnalysisResult, String> {

    Page<AnalysisResult> findByUserEmailOrderByCreatedAtDesc(
            String userEmail,
            Pageable pageable
    );

    Page<AnalysisResult> findByUserEmailAndAnalysisTypeOrderByCreatedAtDesc(
            String userEmail,
            String analysisType,
            Pageable pageable
    );

    Page<AnalysisResult> findByUserEmailAndModelOrderByCreatedAtDesc(
            String userEmail,
            String model,
            Pageable pageable
    );

    Page<AnalysisResult> findByUserEmailAndAnalysisTypeAndModelOrderByCreatedAtDesc(
            String userEmail,
            String analysisType,
            String model,
            Pageable pageable
    );
}