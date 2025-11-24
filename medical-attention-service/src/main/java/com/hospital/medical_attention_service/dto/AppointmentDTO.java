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
public class AppointmentDTO {
    private Long id;
    private Long scheduleId;
    private Long patientId;
    private LocalDateTime solicitationDateTime;
    private BigDecimal finalCost;
}
