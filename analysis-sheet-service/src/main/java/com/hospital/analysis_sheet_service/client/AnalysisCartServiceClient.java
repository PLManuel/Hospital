package com.hospital.analysis_sheet_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.analysis_sheet_service.dto.AnalysisCartItemDTO;
@FeignClient(name = "analysis-cart-service")
public interface AnalysisCartServiceClient {
    @GetMapping("/analysis-cart/listar")
    List<AnalysisCartItemDTO> getCartItems();
}
