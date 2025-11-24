package com.hospital.medical_attention_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive(message = "La ID de la cita debe ser un número positivo")
    private Long appointmentId;

    @NotNull(message = "El DNI del médico es obligatorio")
    private String doctorDni;

    @NotNull(message = "La ID de la historia médica es obligatoria")
    @Positive(message = "La ID de la historia médica debe ser un número positivo")
    private Long medicalHistoryId;

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Size(min = 3, max = 255, message = "El diagnóstico debe tener entre 3 y 255 caracteres")
    private String diagnosis;
    
    @Size(max = 255, message = "El tratamiento no puede exceder los 255 caracteres")
    private String treatment;

    @Size(max = 255, message = "La nota no puede exceder los 255 caracteres")
    private String notes;
}
