package com.hospital.medical_attention_service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
  private Long id;
  private String dni;
  private String name;
  private String lastname;
  private LocalDate birthdate;
  private String gender;
}
