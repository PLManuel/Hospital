package com.hospital.analysis_cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisCartItemResponseDTO {
    private Long id;
    private TypeAnalysisDTO typeAnalysis;
    private String observations;
    private Integer quantity;
}
