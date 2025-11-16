package com.hospital.schedule_service.service;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hospital.schedule_service.client.DoctorServiceClient;
import com.hospital.schedule_service.client.EmployeeServiceClient;
import com.hospital.schedule_service.client.OfficeServiceClient;
import com.hospital.schedule_service.client.SpecialtyServiceClient;
import com.hospital.schedule_service.dto.DoctorDTO;
import com.hospital.schedule_service.dto.EmployeeDTO;
import com.hospital.schedule_service.dto.OfficeDTO;
import com.hospital.schedule_service.dto.ScheduleRequestDTO;
import com.hospital.schedule_service.dto.ScheduleResponseDTO;
import com.hospital.schedule_service.dto.ScheduleUpdateDTO;
import com.hospital.schedule_service.dto.SpecialtyDTO;
import com.hospital.schedule_service.model.Schedule;
import com.hospital.schedule_service.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final DoctorServiceClient doctorServiceClient;
  private final EmployeeServiceClient employeeServiceClient;
  private final SpecialtyServiceClient specialtyServiceClient;
  private final OfficeServiceClient officeServiceClient;
  private final ModelMapper modelMapper;

  public ScheduleResponseDTO createSchedule(ScheduleRequestDTO dto) {
    if (dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())) {
      throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
    }

    SpecialtyDTO specialty = specialtyServiceClient.getSpecialtyById(dto.getSpecialtyId());
    if (specialty == null) {
      throw new IllegalArgumentException("Especialidad no encontrada con ID: " + dto.getSpecialtyId());
    }
    if (Boolean.FALSE.equals(specialty.getStatus())) {
      throw new IllegalArgumentException("La especialidad está deshabilitada");
    }

    EmployeeDTO employee = employeeServiceClient.getEmployeeByDni(dto.getEmployeeDni());
    if (employee == null) {
      throw new IllegalArgumentException("Empleado no encontrado con DNI: " + dto.getEmployeeDni());
    }
    if (Boolean.FALSE.equals(employee.getIsEnabled())) {
      throw new IllegalArgumentException("El empleado está deshabilitado");
    }

    DoctorDTO doctor = doctorServiceClient.getDoctorByDni(dto.getDoctorDni());
    if (doctor == null) {
      throw new IllegalArgumentException("Doctor no encontrado con DNI: " + dto.getDoctorDni());
    }

    boolean doctorHasSpecialty = doctor.getSpecialties() != null &&
        doctor.getSpecialties().stream()
            .anyMatch(s -> s != null && s.getId() != null && s.getId().equals(dto.getSpecialtyId()));

    if (!doctorHasSpecialty) {
      throw new IllegalArgumentException("El doctor no está asignado a la especialidad: " + specialty.getName());
    }

    OfficeDTO office = officeServiceClient.getOfficeById(dto.getOfficeId());
    if (office == null) {
      throw new IllegalArgumentException("Consultorio no encontrado con ID: " + dto.getOfficeId());
    }
    if (Boolean.FALSE.equals(office.getStatus())) {
      throw new IllegalArgumentException("El consultorio está deshabilitado");
    }

    boolean officeHasSpecialty = office.getSpecialtyIds() != null &&
        office.getSpecialtyIds().contains(dto.getSpecialtyId());

    if (!officeHasSpecialty) {
      throw new IllegalArgumentException("El consultorio no está asignado a la especialidad: " + specialty.getName());
    }

    List<Schedule> schedules = scheduleRepository.findByStartTimeBetween(dto.getStartTime(), dto.getEndTime());
    schedules.stream()
        .filter(existingSchedule -> existingSchedule.getOfficeId().equals(dto.getOfficeId()) &&
            dto.getStartTime().isBefore(existingSchedule.getEndTime()) &&
            dto.getEndTime().isAfter(existingSchedule.getStartTime()))
        .findFirst()
        .ifPresent(existingSchedule -> {
          throw new IllegalArgumentException("El consultorio ya tiene una programación en este horario");
        });

    Schedule schedule = Schedule.builder()
        .doctorDni(dto.getDoctorDni())
        .employeeDni(dto.getEmployeeDni())
        .specialtyId(dto.getSpecialtyId())
        .officeId(dto.getOfficeId())
        .date(dto.getDate())
        .startTime(dto.getStartTime())
        .endTime(dto.getEndTime())
        .status(true)
        .build();

    @SuppressWarnings("null")
    Schedule savedSchedule = scheduleRepository.save(schedule);

    ScheduleResponseDTO response = ScheduleResponseDTO.builder()
        .id(savedSchedule.getId())
        .employee(employee)
        .doctor(doctor)
        .specialty(specialty)
        .office(office)
        .date(savedSchedule.getDate())
        .startTime(savedSchedule.getStartTime())
        .endTime(savedSchedule.getEndTime())
        .status(savedSchedule.getStatus())
        .build();

    return response;
  }

  public List<ScheduleResponseDTO> getAllSchedules() {
    List<Schedule> schedules = scheduleRepository.findAll();
    return schedules.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public ScheduleResponseDTO getScheduleById(Long id) {
    @SuppressWarnings("null")
    Schedule schedule = scheduleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Horario no encontrado con ID: " + id));
    return mapToResponseDTO(schedule);
  }

  public List<ScheduleResponseDTO> getSchedulesByDoctorDni(String doctorDni) {
    List<Schedule> schedules = scheduleRepository.findByDoctorDni(doctorDni);
    return schedules.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<ScheduleResponseDTO> getSchedulesBySpecialtyId(Long specialtyId) {
    List<Schedule> schedules = scheduleRepository.findBySpecialtyId(specialtyId);
    return schedules.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<ScheduleResponseDTO> getSchedulesByDoctorDniAndSpecialtyId(String doctorDni, Long specialtyId) {
    List<Schedule> schedules = scheduleRepository.findByDoctorDniAndSpecialtyId(doctorDni, specialtyId);
    return schedules.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<ScheduleResponseDTO> getSchedulesByOfficeId(Long officeId) {
    List<Schedule> schedules = scheduleRepository.findByOfficeId(officeId);
    return schedules.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<ScheduleResponseDTO> getSchedulesByTimeRange(LocalTime startTime, LocalTime endTime) {
    if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
      throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
    }
    List<Schedule> schedules = scheduleRepository.findByStartTimeBetween(startTime, endTime);
    return schedules.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public void disableSchedule(Long id) {
    @SuppressWarnings("null")
    Schedule schedule = scheduleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Horario no encontrado con ID: " + id));
    schedule.setStatus(false);
    scheduleRepository.save(schedule);
  }

  public void enableSchedule(Long id) {
    @SuppressWarnings("null")
    Schedule schedule = scheduleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Horario no encontrado con ID: " + id));
    schedule.setStatus(true);
    scheduleRepository.save(schedule);
  }

  public ScheduleResponseDTO updateSchedule(Long id, ScheduleUpdateDTO scheduleUpdateDTO) {
    @SuppressWarnings("null")
    Schedule schedule = scheduleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Horario no encontrado con ID: " + id));

    OfficeDTO office = officeServiceClient.getOfficeById(scheduleUpdateDTO.getOfficeId());
    if (office == null) {
      throw new IllegalArgumentException("Consultorio no encontrado con ID: " + scheduleUpdateDTO.getOfficeId());
    }
    if (Boolean.FALSE.equals(office.getStatus())) {
      throw new IllegalArgumentException("El consultorio está deshabilitado");
    }

    boolean officeHasSpecialty = office.getSpecialtyIds() != null &&
        office.getSpecialtyIds().contains(schedule.getSpecialtyId());

    if (!officeHasSpecialty) {
      throw new IllegalArgumentException(
          "El consultorio no está asignado a la especialidad con ID: " + schedule.getOfficeId());
    }

    List<Schedule> schedules = scheduleRepository.findByStartTimeBetween(schedule.getStartTime(),
        schedule.getEndTime());
    schedules.stream()
        .filter(existingSchedule -> existingSchedule.getOfficeId().equals(scheduleUpdateDTO.getOfficeId()) &&
            schedule.getStartTime().isBefore(existingSchedule.getEndTime()) &&
            schedule.getEndTime().isAfter(existingSchedule.getStartTime()))
        .findFirst()
        .ifPresent(existingSchedule -> {
          throw new IllegalArgumentException("El consultorio ya tiene una programación en este horario");
        });

    schedule.setOfficeId(scheduleUpdateDTO.getOfficeId());

    Schedule updatedSchedule = scheduleRepository.save(schedule);

    return mapToResponseDTO(updatedSchedule);
  }

  private ScheduleResponseDTO mapToResponseDTO(Schedule schedule) {
    ScheduleResponseDTO response = modelMapper.map(schedule, ScheduleResponseDTO.class);

    response.setEmployee(getServiceData(
        () -> employeeServiceClient.getEmployeeByDni(schedule.getEmployeeDni())));
    response.setDoctor(getServiceData(
        () -> doctorServiceClient.getDoctorByDni(schedule.getDoctorDni())));
    response.setSpecialty(getServiceData(
        () -> specialtyServiceClient.getSpecialtyById(schedule.getSpecialtyId())));
    response.setOffice(getServiceData(
        () -> officeServiceClient.getOfficeById(schedule.getOfficeId())));

    return response;
  }

  private <T> T getServiceData(Supplier<T> serviceCall) {
    try {
      return serviceCall.get();
    } catch (Exception e) {
      return null;
    }
  }
}
