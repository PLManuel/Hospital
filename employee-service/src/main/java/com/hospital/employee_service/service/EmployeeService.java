package com.hospital.employee_service.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hospital.employee_service.dto.EmployeeRequestDTO;
import com.hospital.employee_service.dto.RegisterRequestDTO;
import com.hospital.employee_service.dto.DoctorRequestDTO;
import com.hospital.employee_service.dto.EmployeeDTO;
import com.hospital.employee_service.client.AuthServiceClient;
import com.hospital.employee_service.client.DoctorServiceClient;
import com.hospital.employee_service.client.SpecialtyServiceClient;
import com.hospital.employee_service.exception.CustomException;
import com.hospital.employee_service.model.Employee;
import com.hospital.employee_service.model.Role;
import com.hospital.employee_service.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final AuthServiceClient authServiceClient;
  private final DoctorServiceClient doctorServiceClient;
  private final SpecialtyServiceClient specialtyServiceClient;
  private final ModelMapper modelMapper;

  @Transactional
  public EmployeeDTO createEmployee(EmployeeRequestDTO dto) {

    if (employeeRepository.findByDni(dto.getDni()).isPresent()) {
      throw new CustomException("El empleado con DNI: " + dto.getDni() + " ya existe", HttpStatus.CONFLICT);
    }

    if (dto.getRole() == Role.DOCTOR && dto.getSpecialtyIds() != null && !dto.getSpecialtyIds().isEmpty()) {
      Set<Long> existingSpecialtyIds = specialtyServiceClient.getExistingSpecialtyIds(dto.getSpecialtyIds());

      if (!existingSpecialtyIds.equals(dto.getSpecialtyIds())) {
        Set<Long> invalidIds = dto.getSpecialtyIds().stream()
            .filter(id -> !existingSpecialtyIds.contains(id))
            .collect(Collectors.toSet());
        throw new CustomException(
            "Las siguientes especialidades no existen: " + invalidIds, HttpStatus.BAD_REQUEST);
      }
    }

    Employee employee = modelMapper.map(dto, Employee.class);

    @SuppressWarnings("null")
    Employee savedEmployee = employeeRepository.save(employee);

    EmployeeDTO employeeDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);
    employeeDTO.setRole(dto.getRole());

    RegisterRequestDTO authRequest = modelMapper.map(dto, RegisterRequestDTO.class);
    authRequest.setPassword(dto.getPassword());
    authServiceClient.registerUser(authRequest);

    if (dto.getRole() == Role.DOCTOR) {
      DoctorRequestDTO doctorRequest = modelMapper.map(dto, DoctorRequestDTO.class);
      doctorServiceClient.createDoctor(doctorRequest);
    }
    try {
      EmployeeDTO authData = authServiceClient.getUserByDni(savedEmployee.getDni());
      employeeDTO.setIsEnabled(authData.getIsEnabled());
    } catch (Exception e) {
      employeeDTO.setIsEnabled(null);
    }

    return employeeDTO;
  }

  public List<EmployeeDTO> getAllEmployees() {
    List<Employee> employees = employeeRepository.findAll();
    return employees.stream()
        .map(employee -> {
          EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
          try {
            EmployeeDTO authData = authServiceClient.getUserByDni(employee.getDni());
            employeeDTO.setRole(authData.getRole());
            employeeDTO.setIsEnabled(authData.getIsEnabled());
          } catch (Exception e) {
            employeeDTO.setRole(null);
            employeeDTO.setIsEnabled(null);
          }
          return employeeDTO;
        })
        .collect(Collectors.toList());
  }

  public EmployeeDTO getEmployeeByDni(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new CustomException("Empleado no encontrado con DNI: " + dni, HttpStatus.NOT_FOUND));

    EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

    try {
      EmployeeDTO authData = authServiceClient.getUserByDni(dni);
      employeeDTO.setRole(authData.getRole());
      employeeDTO.setIsEnabled(authData.getIsEnabled());
    } catch (Exception e) {
      employeeDTO.setRole(null);
      employeeDTO.setIsEnabled(null);
    }

    return employeeDTO;
  }

  public EmployeeDTO getEmployeeById(Long id) {
    @SuppressWarnings("null")
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new CustomException("Empleado no encontrado con ID: " + id, HttpStatus.NOT_FOUND));

    EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

    try {
      EmployeeDTO authData = authServiceClient.getUserByDni(employee.getDni());
      employeeDTO.setRole(authData.getRole());
      employeeDTO.setIsEnabled(authData.getIsEnabled());
    } catch (Exception e) {
      employeeDTO.setRole(null);
      employeeDTO.setIsEnabled(null);
    }

    return employeeDTO;
  }
}