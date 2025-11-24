package com.hospital.medical_attention_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hospital.medical_attention_service.client.AppointmentServiceClient;
import com.hospital.medical_attention_service.client.DoctorServiceClient;
import com.hospital.medical_attention_service.client.MedicalHistoryServiceClient;
import com.hospital.medical_attention_service.dto.DoctorDTO;
import com.hospital.medical_attention_service.dto.MedicalAttentionRequestDTO;
import com.hospital.medical_attention_service.dto.MedicalAttentionResponse;
import com.hospital.medical_attention_service.exception.CustomException;
import com.hospital.medical_attention_service.model.MedicalAttention;
import com.hospital.medical_attention_service.repository.MedicalAttentionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalAttentionService {
    private final MedicalAttentionRepository repository;
    private final AppointmentServiceClient appointmentClient;
    private final DoctorServiceClient doctorClient;
    private final MedicalHistoryServiceClient historyClient;

    // CREATE
    @Transactional
    public MedicalAttentionResponse create(MedicalAttentionRequestDTO dto) {

        // Validar cita
        try {
            if (appointmentClient.getAppointmentById(dto.getAppointmentId()) == null)
                throw new IllegalStateException();
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY, 
                "No se pudo validar la cita con ID " + dto.getAppointmentId());
        }

        // Validar doctor
        DoctorDTO doctor = doctorClient.getDoctorByDni(dto.getDoctorDni());
        if (doctor == null) {
        throw new CustomException("Doctor no encontrado con DNI: " + dto.getDoctorDni(), HttpStatus.NOT_FOUND);
        }

        // Validar historia médica
        try {
            if (historyClient.getMedicalHistoryById(dto.getMedicalHistoryId()) == null)
                throw new IllegalStateException();
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY, 
                "No se pudo validar la historia médica con ID " + dto.getMedicalHistoryId());
        }

        MedicalAttention entity = MedicalAttention.builder()
                .appointmentId(dto.getAppointmentId())
                .doctorDni(dto.getDoctorDni())
                .medicalHistoryId(dto.getMedicalHistoryId())
                .attentionDateTime(LocalDateTime.now())
                .diagnosis(dto.getDiagnosis())
                .treatment(dto.getTreatment())
                .notes(dto.getNotes())
                .build();

        return toResponse(repository.save(entity));
    }

    // UPDATE
    @Transactional
    public MedicalAttentionResponse update(Long id, MedicalAttentionRequestDTO dto) {
        MedicalAttention entity = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Atención médica no encontrada"));

        entity.setDiagnosis(dto.getDiagnosis());
        entity.setTreatment(dto.getTreatment());
        entity.setNotes(dto.getNotes());

        return toResponse(repository.save(entity));
    }

    // DELETE
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atención no encontrada");

        repository.deleteById(id);
    }

    // GET by ID
    @Transactional
    public MedicalAttentionResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Atención médica no encontrada"));
    }

    // GET by doctor
    @Transactional
    public List<MedicalAttentionResponse> getByDoctorDni(String doctorDni) {
        return repository.findByDoctorDni(doctorDni)
                .stream().map(this::toResponse).toList();
    }

    // LIST
    @Transactional
    public List<MedicalAttentionResponse> list() {
        return repository.findAll().stream()
                .map(this::toResponse).toList();
    }

    // Converter
    private MedicalAttentionResponse toResponse(MedicalAttention m) {
        return MedicalAttentionResponse.builder()
                .id(m.getId())
                .appointmentId(m.getAppointmentId())
                .doctorDni(m.getDoctorDni())
                .medicalHistoryId(m.getMedicalHistoryId())
                .attentionDateTime(m.getAttentionDateTime())
                .diagnosis(m.getDiagnosis())
                .treatment(m.getTreatment())
                .notes(m.getNotes())
                .build();
    }
}
