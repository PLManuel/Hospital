package com.hospital.appointment_service.dto;

import com.hospital.appointment_service.model.Status;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {
  @NotNull(message = "La ID del horario es obligatorio")
  private Long scheduleId;

  @NotNull(message = "El DNI del empleado es obligatorio")
  private String employeeDni;

  @NotNull(message = "La ID del paciente es obligatorio")
  private Long patientId;

  @Builder.Default
  private Status status = Status.PENDIENTE;
}
