package com.hospital.medical_history_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.medical_history_service.dto.MedicalAttentionSimpleDTO;

@FeignClient(name = "medical-attention-service")
public interface MedicalAttentionServiceClient {
    
    @GetMapping("/medical-attention/medical-history/{medicalHistoryId}/simple")
    List<MedicalAttentionSimpleDTO> getAttentionsByMedicalHistoryId(@PathVariable Long medicalHistoryId);
}
