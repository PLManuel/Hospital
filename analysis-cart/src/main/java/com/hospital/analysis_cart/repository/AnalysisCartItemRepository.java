package com.hospital.analysis_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.analysis_cart.model.AnalysisCartItem;

public interface AnalysisCartItemRepository extends JpaRepository<AnalysisCartItem, Long> {
    
}
