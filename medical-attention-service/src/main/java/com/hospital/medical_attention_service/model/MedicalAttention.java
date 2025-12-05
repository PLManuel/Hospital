package com.hospital.medical_attention_service.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_attention")
public class MedicalAttention {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long appointmentId;

  @Column(nullable = false)
  private String doctorDni;

  @Column(nullable = false)
  private Long medicalHistoryId;

  @Column(nullable = false)
  private LocalDateTime attentionDateTime;

  @Column(nullable = false)
  private String diagnosis;

  @Column(nullable = false)
  private String treatment;

  @Column(nullable = true)
  private String notes;
}
