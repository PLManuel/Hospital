package com.hospital.medicine_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;

    @NotBlank(message = "La concentración es obligatoria")
    @Size(max = 50, message = "La concentración no puede exceder 50 caracteres")
    private String concentration;

    @NotBlank(message = "La presentación es obligatoria")
    @Size(max = 255, message = "La presentación no puede exceder los 255 caracteres")
    private String presentation;
}
