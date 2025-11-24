package com.hospital.medical_attention_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hospital.medical_attention_service.client.AppointmentServiceClient;
import com.hospital.medical_attention_service.client.DoctorServiceClient;
import com.hospital.medical_attention_service.client.MedicalHistoryServiceClient;
import com.hospital.medical_attention_service.dto.AppointmentDTO;
import com.hospital.medical_attention_service.dto.DoctorDTO;
import com.hospital.medical_attention_service.dto.MedicalAttentionRequestDTO;
import com.hospital.medical_attention_service.dto.MedicalAttentionResponse;
import com.hospital.medical_attention_service.dto.MedicalHistoryDTO;
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

        // Traer y validar objetos remotos
        AppointmentDTO appointment = getAppointmentOrThrow(dto.getAppointmentId());
        DoctorDTO doctor = getDoctorOrThrow(dto.getDoctorDni());
        MedicalHistoryDTO history = getHistoryOrThrow(dto.getMedicalHistoryId());

        MedicalAttention entity = MedicalAttention.builder()
                .appointmentId(dto.getAppointmentId())
                .doctorDni(dto.getDoctorDni())
                .medicalHistoryId(dto.getMedicalHistoryId())
                .attentionDateTime(LocalDateTime.now())
                .diagnosis(dto.getDiagnosis())
                .treatment(dto.getTreatment())
                .notes(dto.getNotes())
                .build();

        MedicalAttention saved = repository.save(entity);

        // Armamos respuesta rica, como en Schedule
        return MedicalAttentionResponse.builder()
                .id(saved.getId())
                .appointment(appointment)
                .doctor(doctor)
                .medicalHistory(history)
                .attentionDateTime(saved.getAttentionDateTime())
                .diagnosis(saved.getDiagnosis())
                .treatment(saved.getTreatment())
                .notes(saved.getNotes())
                .build();
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

    private MedicalAttentionResponse toResponse(MedicalAttention m) {

        AppointmentDTO appointment = getAppointmentOrThrow(m.getAppointmentId());
        DoctorDTO doctor = getDoctorOrThrow(m.getDoctorDni());
        MedicalHistoryDTO history = getHistoryOrThrow(m.getMedicalHistoryId());

        return MedicalAttentionResponse.builder()
                .id(m.getId())
                .appointment(appointment)
                .doctor(doctor)
                .medicalHistory(history)
                .attentionDateTime(m.getAttentionDateTime())
                .diagnosis(m.getDiagnosis())
                .treatment(m.getTreatment())
                .notes(m.getNotes())
                .build();
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

    private AppointmentDTO getAppointmentOrThrow(Long id) {
        try {
            AppointmentDTO dto = appointmentClient.getAppointmentById(id);
            if (dto == null) {
                throw new IllegalStateException("Cita no encontrada");
            }
            return dto;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "No se pudo validar la cita con ID " + id,
                ex
            );
        }
    }

    private DoctorDTO getDoctorOrThrow(String dni) {
        try {
            DoctorDTO dto = doctorClient.getDoctorByDni(dni);
            if (dto == null) {
                throw new CustomException("Doctor no encontrado con DNI: " + dni, HttpStatus.NOT_FOUND);
            }
            return dto;
        } catch (CustomException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "No se pudo validar el doctor con DNI " + dni,
                ex
            );
        }
    }

    private MedicalHistoryDTO getHistoryOrThrow(Long id) {
        try {
            MedicalHistoryDTO dto = historyClient.getMedicalHistoryById(id);
            if (dto == null) {
                throw new IllegalStateException("Historia no encontrada");
            }
            return dto;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "No se pudo validar la historia médica con ID " + id,
                ex
            );
        }
    }
}
