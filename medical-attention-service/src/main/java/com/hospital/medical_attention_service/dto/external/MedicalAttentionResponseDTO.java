package com.hospital.medical_attention_service.dto.external;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalAttentionResponseDTO {
    private Long id;
    private LocalDateTime attentionDateTime;
    private String diagnosis;
    private String treatment;
    private String notes;
}
