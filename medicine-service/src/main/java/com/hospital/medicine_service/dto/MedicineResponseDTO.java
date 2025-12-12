package com.hospital.medicine_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String concentration;
    private String presentation;
    private Boolean status;
}
