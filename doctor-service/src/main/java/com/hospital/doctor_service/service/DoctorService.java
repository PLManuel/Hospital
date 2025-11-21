package com.hospital.doctor_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hospital.doctor_service.client.EmployeeServiceClient;
import com.hospital.doctor_service.client.SpecialtyServiceClient;
import com.hospital.doctor_service.dto.DoctorRequestDTO;
import com.hospital.doctor_service.dto.DoctorResponseDTO;
import com.hospital.doctor_service.dto.EmployeeDTO;
import com.hospital.doctor_service.dto.SpecialtyDTO;
import com.hospital.doctor_service.exception.CustomException;
import com.hospital.doctor_service.model.Doctor;
import com.hospital.doctor_service.repository.DoctorRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

  private final DoctorRepository doctorRepository;
  private final EmployeeServiceClient employeeServiceClient;
  private final SpecialtyServiceClient specialtyServiceClient;
  private final ModelMapper modelMapper;

  private String generateDoctorCode() {
    long count = doctorRepository.count();
    Long nextId = count + 1;
    String paddedId = String.format("%04d", nextId);
    return "DOC-" + paddedId;
  }

  @Transactional
  public void createDoctor(DoctorRequestDTO dto) {
    if (doctorRepository.findByDni(dto.getDni()).isPresent()) {
      throw new CustomException("Ya existe un doctor con DNI: " + dto.getDni(), HttpStatus.CONFLICT);
    }

    String newCode = generateDoctorCode();
    Doctor doctor = modelMapper.map(dto, Doctor.class);
    doctor.setCodigo(newCode);
    Set<Long> specialtyIds = dto.getSpecialtyIds();
    doctor.setSpecialtyIds(specialtyIds != null && !specialtyIds.isEmpty() ? specialtyIds : Set.of());
    doctorRepository.save(doctor);
  }

  public List<DoctorResponseDTO> getAllDoctors() {
    List<Doctor> doctors = doctorRepository.findAll();

    return doctors.stream()
        .map(doctor -> {
          DoctorResponseDTO responseDTO = modelMapper.map(doctor, DoctorResponseDTO.class);

          try {
            EmployeeDTO employeeData = employeeServiceClient.getEmployeeByDni(doctor.getDni());
            responseDTO.setName(employeeData.getName());
            responseDTO.setLastname(employeeData.getLastname());
          } catch (Exception e) {
            responseDTO.setName(null);
            responseDTO.setLastname(null);
          }

          List<SpecialtyDTO> specialties = new ArrayList<>();
          if (doctor.getSpecialtyIds() != null && !doctor.getSpecialtyIds().isEmpty()) {
            for (Long specialtyId : doctor.getSpecialtyIds()) {
              try {
                SpecialtyDTO specialty = specialtyServiceClient.getSpecialtyById(specialtyId);
                specialties.add(specialty);
              } catch (Exception e) {
              }
            }
          }
          responseDTO.setSpecialties(specialties);

          return responseDTO;
        })
        .collect(Collectors.toList());
  }

  public DoctorResponseDTO getDoctorByDni(String dni) {
    Doctor doctor = doctorRepository.findByDni(dni)
        .orElseThrow(() -> new CustomException("Doctor no encontrado con DNI: " + dni, HttpStatus.NOT_FOUND));

    DoctorResponseDTO responseDTO = modelMapper.map(doctor, DoctorResponseDTO.class);

    try {
      EmployeeDTO employeeData = employeeServiceClient.getEmployeeByDni(dni);
      responseDTO.setName(employeeData.getName());
      responseDTO.setLastname(employeeData.getLastname());
    } catch (Exception e) {
      responseDTO.setName(null);
      responseDTO.setLastname(null);
    }

    List<SpecialtyDTO> specialties = new ArrayList<>();
    if (doctor.getSpecialtyIds() != null && !doctor.getSpecialtyIds().isEmpty()) {
      for (Long specialtyId : doctor.getSpecialtyIds()) {
        try {
          SpecialtyDTO specialty = specialtyServiceClient.getSpecialtyById(specialtyId);
          specialties.add(specialty);
        } catch (Exception e) {
        }
      }
    }
    responseDTO.setSpecialties(specialties);

    return responseDTO;
  }

  public List<DoctorResponseDTO> getDoctorsBySpecialtyId(Long specialtyId) {
    List<Doctor> doctors = doctorRepository.findBySpecialtyIdsContaining(specialtyId);

    return doctors.stream()
        .map(doctor -> {
          DoctorResponseDTO responseDTO = modelMapper.map(doctor, DoctorResponseDTO.class);

          try {
            EmployeeDTO employeeData = employeeServiceClient.getEmployeeByDni(doctor.getDni());
            responseDTO.setName(employeeData.getName());
            responseDTO.setLastname(employeeData.getLastname());
          } catch (Exception e) {
            responseDTO.setName(null);
            responseDTO.setLastname(null);
          }

          List<SpecialtyDTO> specialties = new ArrayList<>();
          if (doctor.getSpecialtyIds() != null && !doctor.getSpecialtyIds().isEmpty()) {
            for (Long specId : doctor.getSpecialtyIds()) {
              try {
                SpecialtyDTO specialty = specialtyServiceClient.getSpecialtyById(specId);
                specialties.add(specialty);
              } catch (Exception e) {
              }
            }
          }
          responseDTO.setSpecialties(specialties);

          return responseDTO;
        })
        .collect(Collectors.toList());
  }

}