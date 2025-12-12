package com.hospital.analysis_cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.analysis_cart.dto.AnalysisCartItemRequestDTO;
import com.hospital.analysis_cart.dto.AnalysisCartItemResponseDTO;
import com.hospital.analysis_cart.service.AnalysisCartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/analysis-cart")
public class AnalysisCartController {
    @Autowired
    private AnalysisCartService service;

    @PostMapping("/agregar")
    public ResponseEntity<AnalysisCartItemResponseDTO> agregar(@Valid @RequestBody AnalysisCartItemRequestDTO dto) {
        AnalysisCartItemResponseDTO item = service.agregar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @DeleteMapping("/quitar/{id}")
    public void quitar(@PathVariable Long id) {
        service.quitar(id);
    }

    @GetMapping("/listar")
    public List<AnalysisCartItemResponseDTO> listar() {
        return service.listar();
    }

    @DeleteMapping("/nuevo")
    public void nuevo() {
        service.nuevo();
    }
}
