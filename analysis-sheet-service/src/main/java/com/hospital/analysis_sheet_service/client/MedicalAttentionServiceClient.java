package com.hospital.analysis_sheet_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.analysis_sheet_service.dto.MedicalAttentionDTO;

@FeignClient(name = "medical-attention-service")
public interface MedicalAttentionServiceClient {
    @GetMapping("/medical-attention/{medicalHistoryId}/simple")
    List<MedicalAttentionDTO> getAttentionsByMedicalHistoryId(@PathVariable Long medicalHistoryId);
}
