package com.value.slmbridge.repository;

import com.value.slmbridge.entity.AnalysisResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnalysisResultRepository extends MongoRepository<AnalysisResult, String> {

    List<AnalysisResult> findByAnalysisTypeOrderByCreatedAtDesc(String analysisType);
}