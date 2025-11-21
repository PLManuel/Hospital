package com.hospital.appointment_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.appointment_service.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  List<Appointment> findByScheduleId(Long scheduleId);

  List<Appointment> findByEmployeeDni(String employeeDni);

  List<Appointment> findByOfficeId(Long officeId);

  List<Appointment> findBySolicitationDateTimeBetween(LocalDateTime start, LocalDateTime end);

  boolean existsByScheduleId(Long scheduleId);

}
