package com.hospital.medical_attention_service.dto.external;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDTO {
    private Long id;
    private String dni;
    private String codigo;
    private String name;
    private String lastname;
    private List<SpecialtyResponseDTO> specialties;
}
