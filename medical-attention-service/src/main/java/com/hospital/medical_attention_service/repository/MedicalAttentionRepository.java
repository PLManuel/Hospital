package com.hospital.medical_attention_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.medical_attention_service.model.MedicalAttention;

public interface MedicalAttentionRepository extends JpaRepository<MedicalAttention, Long> {
  List<MedicalAttention> findByDoctorDni(String doctorDni);
  List<MedicalAttention> findByMedicalHistoryId(Long medicalHistoryId);
}
