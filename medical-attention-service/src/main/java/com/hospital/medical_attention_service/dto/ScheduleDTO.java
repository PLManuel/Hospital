package com.hospital.medical_attention_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {
    private Long id;
    private DoctorDTO doctor;
    private SpecialtyDTO specialty;
    private OfficeDTO office;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean status;
}
