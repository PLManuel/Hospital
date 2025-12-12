package com.hospital.analysis_cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeAnalysisDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean status;
}
