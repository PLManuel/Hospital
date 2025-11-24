package com.hospital.medical_attention_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MedicalAttentionRequestDTO {
    @NotNull(message = "La ID de la cita es obligatoria")
    private Long appointmentId;

    @NotNull(message = "La ID del medico es obligatorio")
    private Long doctorId;

    @NotNull(message = "La ID de la horaria medica es obligatoria")
    private Long medicalHistoryId;

    @NotNull(message = "El diagnostico es obligatorio")
    private String diagnosis;

    @Size(max = 255, message = "El nombre del tratamiento no puede exceder los 255 caracteres")
    private String treatment;

    @Size(max = 255, message = "La nota no puede exceder los 255 caracteres")
    private String notes;
}
