package com.hospital.medical_attention_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.medical_attention_service.dto.external.MedicalHistoryResponseDTO;

@FeignClient(name = "medical-history-service")
public interface MedicalHistoryServiceClient {
  @GetMapping("/medical-histories/{id}")
  MedicalHistoryResponseDTO getMedicalHistoryById(@PathVariable Long id);
}
