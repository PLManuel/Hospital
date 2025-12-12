package com.hospital.analysis_sheet_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.analysis_sheet_service.model.AnalysisSheet;

public interface AnalysisSheetRepository extends JpaRepository<AnalysisSheet, Long> {
    List<AnalysisSheet> findByAttentionId(Long attentionId);
}
