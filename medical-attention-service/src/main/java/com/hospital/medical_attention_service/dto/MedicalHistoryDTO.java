package com.hospital.medical_attention_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryDTO {
  private Long id;
  private Long patientId;
  private String employeeDni;
  private BigDecimal height; 
  private BigDecimal weight;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean status;
}
