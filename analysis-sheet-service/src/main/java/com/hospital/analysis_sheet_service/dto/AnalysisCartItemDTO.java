package com.hospital.analysis_sheet_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisCartItemDTO {
    private Long id;
    private Long typeAnalysisId;
    private Long analysisSheetId;
    private String observations;
    private Integer quantity;
}
