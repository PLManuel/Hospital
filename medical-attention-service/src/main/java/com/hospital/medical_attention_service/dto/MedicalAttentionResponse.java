package com.hospital.medical_attention_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MedicalAttentionResponse {
    private Long id;
    private Long appointmentId;
    private String doctorDni;
    private Long medicalHistoryId;
    private LocalDateTime attentionDateTime;
    private String diagnosis;
    private String treatment;
    private String notes;
}
