package com.hospital.schedule_service.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.schedule_service.dto.ScheduleRequestDTO;
import com.hospital.schedule_service.dto.ScheduleResponseDTO;
import com.hospital.schedule_service.dto.ScheduleUpdateDTO;
import com.hospital.schedule_service.service.ScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleService scheduleService;

  @PostMapping
  public ResponseEntity<ScheduleResponseDTO> createSchedule(@Valid @RequestBody ScheduleRequestDTO dto) {
    ScheduleResponseDTO schedule = scheduleService.createSchedule(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
  }

  @GetMapping
  public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
    List<ScheduleResponseDTO> schedules = scheduleService.getAllSchedules();
    return ResponseEntity.ok(schedules);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable Long id) {
    ScheduleResponseDTO schedule = scheduleService.getScheduleById(id);
    return ResponseEntity.ok(schedule);
  }

  @GetMapping("/doctor/{doctorDni}")
  public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesByDoctorDni(@PathVariable String doctorDni) {
    List<ScheduleResponseDTO> schedules = scheduleService.getSchedulesByDoctorDni(doctorDni);
    return ResponseEntity.ok(schedules);
  }

  @GetMapping("/specialty/{specialtyId}")
  public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesBySpecialtyId(@PathVariable Long specialtyId) {
    List<ScheduleResponseDTO> schedules = scheduleService.getSchedulesBySpecialtyId(specialtyId);
    return ResponseEntity.ok(schedules);
  }

  @GetMapping("/doctor/{doctorDni}/specialty/{specialtyId}")
  public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesByDoctorAndSpecialty(
      @PathVariable String doctorDni,
      @PathVariable Long specialtyId) {
    List<ScheduleResponseDTO> schedules = scheduleService.getSchedulesByDoctorDniAndSpecialtyId(doctorDni,
        specialtyId);
    return ResponseEntity.ok(schedules);
  }

  @GetMapping("/office/{officeId}")
  public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesByOfficeId(@PathVariable Long officeId) {
    List<ScheduleResponseDTO> schedules = scheduleService.getSchedulesByOfficeId(officeId);
    return ResponseEntity.ok(schedules);
  }

  @GetMapping("/time-range/{startTime}/{endTime}")
  public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesByTimeRange(
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
    List<ScheduleResponseDTO> schedules = scheduleService.getSchedulesByTimeRange(startTime, endTime);
    return ResponseEntity.ok(schedules);
  }

  @PatchMapping("/disable/{id}")
  public ResponseEntity<Void> disableSchedule(@PathVariable Long id) {
    scheduleService.disableSchedule(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/enable/{id}")
  public ResponseEntity<Void> enableSchedule(@PathVariable Long id) {
    scheduleService.enableSchedule(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<ScheduleResponseDTO> updateSchedule(
      @PathVariable Long id,
      @Valid @RequestBody ScheduleUpdateDTO dto) {
    ScheduleResponseDTO schedule = scheduleService.updateSchedule(id, dto);
    return ResponseEntity.ok(schedule);
  }

}
