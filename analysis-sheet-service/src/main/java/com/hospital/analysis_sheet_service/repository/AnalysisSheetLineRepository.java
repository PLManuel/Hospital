package com.hospital.analysis_sheet_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.analysis_sheet_service.model.AnalysisSheetLine;

public interface AnalysisSheetLineRepository extends JpaRepository<AnalysisSheetLine, Long> {
    List<AnalysisSheetLine> findByAnalysisSheetId(Long analysisSheetId);
}
