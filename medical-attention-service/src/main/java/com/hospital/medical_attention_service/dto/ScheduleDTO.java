package com.hospital.medical_attention_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

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
  private SpecialtyDTO specialty;
  private String officeId;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private Boolean status;
}
