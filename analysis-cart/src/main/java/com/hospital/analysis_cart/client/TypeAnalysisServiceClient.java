package com.hospital.analysis_cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.analysis_cart.dto.TypeAnalysisDTO;

@FeignClient(name = "type-analysis-service")
public interface TypeAnalysisServiceClient {
    @GetMapping("/type-analysis/{id}")
    TypeAnalysisDTO findById(@PathVariable("id") Long typeAnalysisId);
}
