package com.hospital.analysis_cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisCartItemRequestDTO {
    @NotNull(message = "El ID del tipo de análisis es obligatorio")
    private Long typeAnalysisId;
    private String observations;
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mínimo 1")
    private Integer quantity;
}
