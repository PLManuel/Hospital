package com.hospital.appointment_service.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hospital.appointment_service.client.EmployeeServiceClient;
import com.hospital.appointment_service.client.OfficeServiceClient;
import com.hospital.appointment_service.client.PatientServiceClient;
import com.hospital.appointment_service.client.ScheduleServiceClient;
import com.hospital.appointment_service.dto.AppointmentRequestDTO;
import com.hospital.appointment_service.dto.AppointmentResponseDTO;
import com.hospital.appointment_service.dto.EmployeeDTO;
import com.hospital.appointment_service.dto.OfficeDTO;
import com.hospital.appointment_service.dto.PatientDTO;
import com.hospital.appointment_service.dto.ScheduleDTO;
import com.hospital.appointment_service.model.Appointment;
import com.hospital.appointment_service.model.Status;
import com.hospital.appointment_service.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {
  private final AppointmentRepository appointmentRepository;
  private final ScheduleServiceClient scheduleServiceClient;
  private final EmployeeServiceClient employeeServiceClient;
  private final PatientServiceClient patientServiceClient;
  private final OfficeServiceClient officeServiceClient;
  private final ModelMapper modelMapper;

  public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
    ScheduleDTO schedule = scheduleServiceClient.getScheduleById(dto.getScheduleId());
    if (schedule == null) {
      throw new IllegalArgumentException("Horario no encontrado con ID: " + dto.getScheduleId());
    }
    if (Boolean.FALSE.equals(schedule.getStatus())) {
      throw new IllegalArgumentException("El horario está deshabilitado");
    }

    EmployeeDTO employee = employeeServiceClient.getEmployeeByDni(dto.getEmployeeDni());
    if (employee == null) {
      throw new IllegalArgumentException("Empleado no encontrado con DNI: " + dto.getEmployeeDni());
    }
    if (Boolean.FALSE.equals(employee.getIsEnabled())) {
      throw new IllegalArgumentException("El empleado está deshabilitado");
    }

    PatientDTO patient = patientServiceClient.getPatientById(dto.getPatientId());
    if (patient == null) {
      throw new IllegalArgumentException("Paciente no encontrado con ID: " + dto.getPatientId());
    }
    if (Boolean.FALSE.equals(patient.getStatus())) {
      throw new IllegalArgumentException("El paciente está deshabilitado");
    }

    OfficeDTO office = officeServiceClient.getOfficeById(schedule.getOffice().getId());
    if (office == null) {
      throw new IllegalArgumentException("Consultorio no encontrado con ID: " + schedule.getOffice().getId());
    }
    if (Boolean.FALSE.equals(office.getStatus())) {
      throw new IllegalArgumentException("El consultorio está deshabilitado");
    }

    if (appointmentRepository.existsByScheduleId(dto.getScheduleId())) {
      throw new IllegalArgumentException("El horario ya está asignado a otra cita");
    }

    // BigDecimal finalCost = calculateFinalCost(schedule);
    BigDecimal finalCost = schedule.getSpecialty().getCost();

    Appointment appointment = Appointment.builder()
        .scheduleId(dto.getScheduleId())
        .employeeDni(dto.getEmployeeDni())
        .patientId(dto.getPatientId())
        .officeId(schedule.getOffice().getId())
        .solicitationDateTime(LocalDateTime.now())
        .finalCost(finalCost)
        .status(Status.PENDIENTE)
        .build();

    @SuppressWarnings("null")
    Appointment savedAppointment = appointmentRepository.save(appointment);

    AppointmentResponseDTO response = modelMapper.map(savedAppointment, AppointmentResponseDTO.class);
    response.setSchedule(schedule);
    response.setEmployee(employee);
    response.setPatient(patient);

    return response;
  }

  private BigDecimal calculateFinalCost(ScheduleDTO schedule) {
    Duration duration = Duration.between(schedule.getStartTime(), schedule.getEndTime());
    long minutes = duration.toMinutes();
    BigDecimal costPerHour = schedule.getSpecialty().getCost();
    BigDecimal minutesDecimal = new BigDecimal(minutes);
    BigDecimal hours = minutesDecimal.divide(new BigDecimal(60), 2, java.math.RoundingMode.HALF_UP);

    return hours.multiply(costPerHour).setScale(2, java.math.RoundingMode.HALF_UP);
  }

  public List<AppointmentResponseDTO> getAllAppointments() {
    List<Appointment> appointments = appointmentRepository.findAll();
    return appointments.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public AppointmentResponseDTO getAppointmentById(Long id) {
    @SuppressWarnings("null")
    Appointment appointment = appointmentRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + id));
    return mapToResponseDTO(appointment);
  }

  public List<AppointmentResponseDTO> getAppointmentsByScheduleId(Long scheduleId) {
    List<Appointment> appointments = appointmentRepository.findByScheduleId(scheduleId);
    return appointments.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<AppointmentResponseDTO> getAppointmentsByEmployeeDni(String employeeDni) {
    List<Appointment> appointments = appointmentRepository.findByEmployeeDni(employeeDni);
    return appointments.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<AppointmentResponseDTO> getAppointmentsByOfficeId(Long officeId) {
    List<Appointment> appointments = appointmentRepository.findByOfficeId(officeId);
    return appointments.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<AppointmentResponseDTO> getAppointmentsByDateTimeRange(LocalDateTime start, LocalDateTime end) {
    if (start.isAfter(end) || start.isEqual(end)) {
      throw new IllegalArgumentException("La fecha/hora de inicio debe ser anterior a la de fin");
    }
    List<Appointment> appointments = appointmentRepository.findBySolicitationDateTimeBetween(start, end);
    return appointments.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public void updateAppointmentStatus(Long id, Status status) {
    @SuppressWarnings("null")
    Appointment appointment = appointmentRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + id));
    appointment.setStatus(status);
    appointmentRepository.save(appointment);
  }

  private <T> T getServiceData(Supplier<T> serviceCall) {
    try {
      return serviceCall.get();
    } catch (Exception e) {
      return null;
    }
  }

  private AppointmentResponseDTO mapToResponseDTO(Appointment appointment) {
    AppointmentResponseDTO response = modelMapper.map(appointment, AppointmentResponseDTO.class);

    response.setSchedule(getServiceData(
        () -> scheduleServiceClient.getScheduleById(appointment.getScheduleId())));
    response.setEmployee(getServiceData(
        () -> employeeServiceClient.getEmployeeByDni(appointment.getEmployeeDni())));
    response.setPatient(getServiceData(
        () -> patientServiceClient.getPatientById(appointment.getPatientId())));

    return response;
  }

}
