package com.hospital.medical_attention_service.dto.external;

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
public class AppointmentResponseDTO {
    private Long id;
    private ScheduleResponseDTO schedule;
    private EmployeeResponseDTO employee;
    private PatientResponseDTO patient;
    private LocalDateTime solicitationDateTime;
    private BigDecimal finalCost;
    private String status;
}
