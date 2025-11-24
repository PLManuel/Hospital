package com.hospital.medical_attention_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.medical_attention_service.dto.MedicalAttentionRequestDTO;
import com.hospital.medical_attention_service.dto.MedicalAttentionResponse;
import com.hospital.medical_attention_service.service.MedicalAttentionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medical-attention")
@RequiredArgsConstructor
public class MedicalAttentionController {
    private final MedicalAttentionService service;

    @PostMapping
    public ResponseEntity<MedicalAttentionResponse> create(
            @Valid @RequestBody MedicalAttentionRequestDTO body) {
        return ResponseEntity.ok(service.create(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalAttentionResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody MedicalAttentionRequestDTO body) {
        return ResponseEntity.ok(service.update(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalAttentionResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/by-doctor/{doctorDni}")
    public ResponseEntity<List<MedicalAttentionResponse>> getByDoctor(
            @PathVariable String doctorDni) {
        return ResponseEntity.ok(service.getByDoctorDni(doctorDni));
    }

    @GetMapping
    public ResponseEntity<List<MedicalAttentionResponse>> list() {
        return ResponseEntity.ok(service.list());
    }
}
