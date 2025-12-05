package com.hospital.medical_attention_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.hospital.medical_attention_service.client.AppointmentServiceClient;
import com.hospital.medical_attention_service.client.DoctorServiceClient;
import com.hospital.medical_attention_service.client.MedicalHistoryServiceClient;
import com.hospital.medical_attention_service.dto.AppointmentDTO;
import com.hospital.medical_attention_service.dto.DoctorDTO;
import com.hospital.medical_attention_service.dto.MedicalAttentionRequestDTO;
import com.hospital.medical_attention_service.dto.MedicalAttentionResponse;
import com.hospital.medical_attention_service.dto.MedicalAttentionSimpleResponse;
import com.hospital.medical_attention_service.dto.MedicalHistoryDTO;
import com.hospital.medical_attention_service.dto.external.AppointmentResponseDTO;
import com.hospital.medical_attention_service.dto.external.MedicalHistoryResponseDTO;
import com.hospital.medical_attention_service.exception.CustomException;
import com.hospital.medical_attention_service.mapper.MedicalAttentionMapper;
import com.hospital.medical_attention_service.model.MedicalAttention;
import com.hospital.medical_attention_service.repository.MedicalAttentionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalAttentionService {
  private final MedicalAttentionRepository medicalAttentionRepository;
  private final AppointmentServiceClient appointmentServiceClient;
  private final DoctorServiceClient doctorServiceClient;
  private final MedicalHistoryServiceClient medicalHistoryServiceClient;
  private final MedicalAttentionMapper mapper;
  private final MedicalAttentionRepository repository;

  @Transactional
  public MedicalAttentionResponse createMedicalAttention(MedicalAttentionRequestDTO request) {
    AppointmentResponseDTO appointmentResponse = appointmentServiceClient
        .getAppointmentById(request.getAppointmentId());
    DoctorDTO doctorResponse = doctorServiceClient.getDoctorByDni(request.getDoctorDni());
    MedicalHistoryResponseDTO medicalHistoryResponse = medicalHistoryServiceClient
        .getMedicalHistoryById(request.getMedicalHistoryId());

    AppointmentDTO appointment = mapper.toAppointmentDTO(appointmentResponse);
    DoctorDTO doctor = mapper.toDoctorDTO(doctorResponse);
    MedicalHistoryDTO medicalHistory = mapper.toMedicalHistoryDTO(medicalHistoryResponse);

    MedicalAttention medicalAttention = MedicalAttention.builder()
        .appointmentId(request.getAppointmentId())
        .doctorDni(request.getDoctorDni())
        .medicalHistoryId(request.getMedicalHistoryId())
        .attentionDateTime(LocalDateTime.now())
        .diagnosis(request.getDiagnosis())
        .treatment(request.getTreatment())
        .notes(request.getNotes())
        .build();

    @SuppressWarnings("null")
    MedicalAttention savedAttention = medicalAttentionRepository.save(medicalAttention);

    return buildMedicalAttentionResponse(savedAttention, appointment, doctor, medicalHistory);
  }

  public MedicalAttentionResponse getMedicalAttentionById(Long id) {
    @SuppressWarnings("null")
    MedicalAttention medicalAttention = medicalAttentionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Atención médica no encontrada con ID: " + id));

    return buildMedicalAttentionResponseFromEntity(medicalAttention);
  }

  public java.util.List<MedicalAttentionResponse> getAllMedicalAttentions() {
    return medicalAttentionRepository.findAll().stream()
        .map(this::buildMedicalAttentionResponseFromEntity)
        .collect(java.util.stream.Collectors.toList());
  }

  public java.util.List<MedicalAttentionResponse> getMedicalAttentionsByDoctorDni(String doctorDni) {
    return medicalAttentionRepository.findByDoctorDni(doctorDni).stream()
        .map(this::buildMedicalAttentionResponseFromEntity)
        .collect(java.util.stream.Collectors.toList());
  }

  // GET by medical history
  @Transactional
  public List<MedicalAttentionSimpleResponse> getByMedicalHistoryId(Long medicalHistoryId) {
    return repository.findByMedicalHistoryId(medicalHistoryId)
        .stream().map(this::toSimpleResponse).toList();
  }

  // GET by medical history (simplified - without medical history reference)
  @Transactional
  public List<MedicalAttentionSimpleResponse> getByMedicalHistoryIdSimple(Long medicalHistoryId) {
    return repository.findByMedicalHistoryId(medicalHistoryId)
        .stream().map(this::toSimpleResponse).toList();
  }

  @SuppressWarnings("null")
  @Transactional
  public void deleteMedicalAttention(Long id) {
    if (!medicalAttentionRepository.existsById(id)) {
      throw new RuntimeException("Atención médica no encontrada con ID: " + id);
    }
    medicalAttentionRepository.deleteById(id);
  }

  private MedicalAttentionResponse buildMedicalAttentionResponseFromEntity(MedicalAttention medicalAttention) {
    AppointmentResponseDTO appointmentResponse = appointmentServiceClient
        .getAppointmentById(medicalAttention.getAppointmentId());
    DoctorDTO doctor = doctorServiceClient.getDoctorByDni(medicalAttention.getDoctorDni());
    MedicalHistoryResponseDTO medicalHistoryResponse = medicalHistoryServiceClient
        .getMedicalHistoryById(medicalAttention.getMedicalHistoryId());

    AppointmentDTO appointment = mapper.toAppointmentDTO(appointmentResponse);
    MedicalHistoryDTO medicalHistory = mapper.toMedicalHistoryDTO(medicalHistoryResponse);

    return buildMedicalAttentionResponse(medicalAttention, appointment, doctor, medicalHistory);
  }

  private MedicalAttentionSimpleResponse toSimpleResponse(MedicalAttention m) {
    DoctorDTO doctor = getDoctorOrThrow(m.getDoctorDni());

    return MedicalAttentionSimpleResponse.builder()
        .id(m.getId())
        .appointmentId(m.getAppointmentId())
        .doctor(doctor)
        .attentionDateTime(m.getAttentionDateTime())
        .diagnosis(m.getDiagnosis())
        .treatment(m.getTreatment())
        .notes(m.getNotes())
        .build();
  }

  private MedicalAttentionResponse buildMedicalAttentionResponse(
      MedicalAttention medicalAttention,
      AppointmentDTO appointment,
      DoctorDTO doctor,
      MedicalHistoryDTO medicalHistory) {
    return MedicalAttentionResponse.builder()
        .id(medicalAttention.getId())
        .appointment(appointment)
        .doctor(doctor)
        .medicalHistory(medicalHistory)
        .attentionDateTime(medicalAttention.getAttentionDateTime())
        .diagnosis(medicalAttention.getDiagnosis())
        .treatment(medicalAttention.getTreatment())
        .notes(medicalAttention.getNotes())
        .build();
  }

  private DoctorDTO getDoctorOrThrow(String dni) {
    try {
      DoctorDTO dto = doctorServiceClient.getDoctorByDni(dni);
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
          ex);
    }
  }
}
