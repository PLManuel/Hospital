package com.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.model.TypeAnalysis;

public interface TypeAnalysisRepository extends JpaRepository<TypeAnalysis, Long> {
    Optional<TypeAnalysis> findByNameIgnoreCase(String name); 
}
