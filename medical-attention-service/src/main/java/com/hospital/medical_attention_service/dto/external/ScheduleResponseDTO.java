package com.hospital.medical_attention_service.dto.external;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {
    private Long id;
    private SpecialtyResponseDTO specialty;
    private String officeId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean status;
}
