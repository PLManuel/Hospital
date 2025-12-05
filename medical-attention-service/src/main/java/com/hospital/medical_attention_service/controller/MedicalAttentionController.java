package com.hospital.medical_attention_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.medical_attention_service.dto.MedicalAttentionRequestDTO;
import com.hospital.medical_attention_service.dto.MedicalAttentionResponse;
import com.hospital.medical_attention_service.dto.MedicalAttentionSimpleResponse;
import com.hospital.medical_attention_service.service.MedicalAttentionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medical-attention")
@RequiredArgsConstructor
public class MedicalAttentionController {
  private final MedicalAttentionService medicalAttentionService;

  @PostMapping
  public ResponseEntity<MedicalAttentionResponse> createMedicalAttention(
      @Valid @RequestBody MedicalAttentionRequestDTO request) {
    MedicalAttentionResponse response = medicalAttentionService.createMedicalAttention(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MedicalAttentionResponse> getMedicalAttentionById(@PathVariable Long id) {
    MedicalAttentionResponse response = medicalAttentionService.getMedicalAttentionById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<MedicalAttentionResponse>> getAllMedicalAttentions() {
    List<MedicalAttentionResponse> responses = medicalAttentionService.getAllMedicalAttentions();
    return ResponseEntity.ok(responses);
  }

  @GetMapping("/doctor/{doctorDni}")
  public ResponseEntity<List<MedicalAttentionResponse>> getMedicalAttentionsByDoctorDni(
      @PathVariable String doctorDni) {
    List<MedicalAttentionResponse> responses = medicalAttentionService.getMedicalAttentionsByDoctorDni(doctorDni);
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMedicalAttention(@PathVariable Long id) {
    medicalAttentionService.deleteMedicalAttention(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/medical-history/{medicalHistoryId}")
  public ResponseEntity<List<MedicalAttentionSimpleResponse>> getByMedicalHistory(
          @PathVariable Long medicalHistoryId) {
      return ResponseEntity.ok(medicalAttentionService.getByMedicalHistoryId(medicalHistoryId));
  }

  @GetMapping("/medical-history/{medicalHistoryId}/simple")
  public ResponseEntity<List<MedicalAttentionSimpleResponse>> getByMedicalHistorySimple(
          @PathVariable Long medicalHistoryId) {
      return ResponseEntity.ok(medicalAttentionService.getByMedicalHistoryIdSimple(medicalHistoryId));
  }
}
