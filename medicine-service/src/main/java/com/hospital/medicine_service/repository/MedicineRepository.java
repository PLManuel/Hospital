package com.hospital.medicine_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.medicine_service.model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Optional<Medicine> findByName(String name);
}
