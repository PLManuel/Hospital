package com.hospital.medical_attention_service.mapper;

import org.springframework.stereotype.Component;

import com.hospital.medical_attention_service.dto.AppointmentDTO;
import com.hospital.medical_attention_service.dto.DoctorDTO;
import com.hospital.medical_attention_service.dto.MedicalHistoryDTO;
import com.hospital.medical_attention_service.dto.PatientDTO;
import com.hospital.medical_attention_service.dto.ScheduleDTO;
import com.hospital.medical_attention_service.dto.SpecialtyDTO;
import com.hospital.medical_attention_service.dto.external.AppointmentResponseDTO;
import com.hospital.medical_attention_service.dto.external.MedicalHistoryResponseDTO;

@Component
public class MedicalAttentionMapper {

  public AppointmentDTO toAppointmentDTO(AppointmentResponseDTO response) {
    if (response == null) {
      return null;
    }

    ScheduleDTO schedule = null;
    if (response.getSchedule() != null) {
      SpecialtyDTO specialty = null;
      if (response.getSchedule().getSpecialty() != null) {
        specialty = SpecialtyDTO.builder()
            .id(response.getSchedule().getSpecialty().getId())
            .name(response.getSchedule().getSpecialty().getName())
            .description(response.getSchedule().getSpecialty().getDescription())
            .build();
      }

      schedule = ScheduleDTO.builder()
          .id(response.getSchedule().getId())
          .specialty(specialty)
          .officeId(response.getSchedule().getOfficeId())
          .date(response.getSchedule().getDate())
          .startTime(response.getSchedule().getStartTime())
          .endTime(response.getSchedule().getEndTime())
          .status(response.getSchedule().getStatus())
          .build();
    }

    PatientDTO patient = null;
    if (response.getPatient() != null) {
      patient = PatientDTO.builder()
          .id(response.getPatient().getId())
          .dni(response.getPatient().getDni())
          .name(response.getPatient().getName())
          .lastname(response.getPatient().getLastname())
          .birthdate(response.getPatient().getBirthdate())
          .gender(response.getPatient().getGender())
          .build();
    }

    return AppointmentDTO.builder()
        .id(response.getId())
        .schedule(schedule)
        .patient(patient)
        .solicitationDateTime(response.getSolicitationDateTime())
        .finalCost(response.getFinalCost())
        .build();
  }

  public DoctorDTO toDoctorDTO(DoctorDTO doctorResponse) {
    if (doctorResponse == null) {
      return null;
    }

    return DoctorDTO.builder()
        .id(doctorResponse.getId())
        .dni(doctorResponse.getDni())
        .name(doctorResponse.getName())
        .lastname(doctorResponse.getLastname())
        .codigo(doctorResponse.getCodigo())
        .build();
  }

  public MedicalHistoryDTO toMedicalHistoryDTO(MedicalHistoryResponseDTO response) {
    if (response == null) {
      return null;
    }

    PatientDTO patient = null;
    if (response.getPatient() != null) {
      patient = PatientDTO.builder()
          .id(response.getPatient().getId())
          .dni(response.getPatient().getDni())
          .name(response.getPatient().getName())
          .lastname(response.getPatient().getLastname())
          .birthdate(response.getPatient().getBirthdate())
          .gender(response.getPatient().getGender())
          .build();
    }

    return MedicalHistoryDTO.builder()
        .id(response.getId())
        .patient(patient)
        .height(response.getHeight())
        .weight(response.getWeight())
        .createdAt(response.getCreatedAt())
        .updatedAt(response.getUpdatedAt())
        .build();
  }
}
