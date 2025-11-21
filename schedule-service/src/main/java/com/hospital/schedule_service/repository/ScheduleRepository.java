package com.hospital.schedule_service.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.schedule_service.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  List<Schedule> findByDoctorDni(String doctorDni);

  List<Schedule> findBySpecialtyId(Long specialtyId);

  List<Schedule> findByDoctorDniAndSpecialtyId(String doctorDni, Long specialtyId);

  List<Schedule> findByOfficeId(Long officeId);

  List<Schedule> findByDate(LocalDate date);

  List<Schedule> findByDateAndOfficeId(LocalDate date, Long officeId);

  List<Schedule> findByStartTimeBetween(LocalTime startTime, LocalTime endTime);

}
