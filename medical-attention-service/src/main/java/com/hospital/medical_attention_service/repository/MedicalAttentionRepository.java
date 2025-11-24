package com.hospital.medical_attention_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.medical_attention_service.model.MedicalAttention;

public interface MedicalAttentionRepository extends JpaRepository<MedicalAttention, Long>{
    
}
