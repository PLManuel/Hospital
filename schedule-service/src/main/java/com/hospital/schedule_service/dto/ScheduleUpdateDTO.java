package com.hospital.schedule_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateDTO {
  @NotNull(message = "El ID del consultorio es obligatorio")
  private Long officeId;
}
