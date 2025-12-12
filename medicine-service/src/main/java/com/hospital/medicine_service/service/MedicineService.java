package com.hospital.medicine_service.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.hospital.medicine_service.dto.MedicineRequestDTO;
import com.hospital.medicine_service.dto.MedicineResponseDTO;
import com.hospital.medicine_service.model.Medicine;
import com.hospital.medicine_service.repository.MedicineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;

    // Crear
    public MedicineResponseDTO create(MedicineRequestDTO dto) {

        if (medicineRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Ya existe una medicina con este nombre");
        }

        Medicine med = Medicine.builder()
                .name(dto.getName().trim())
                .description(dto.getDescription().trim())
                .concentration(dto.getConcentration().trim())
                .presentation(dto.getPresentation().trim())
                .status(true)
                .build();

        return toResponse(medicineRepository.save(med));
    }

    // Editar
    public MedicineResponseDTO update(Long id, MedicineRequestDTO dto) {

        Medicine med = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicina no encontrada"));

        // Evitar nombre duplicado
        medicineRepository.findByName(dto.getName()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new RuntimeException("Ya existe otra medicina con ese nombre");
            }
        });

        med.setName(dto.getName().trim());
        med.setDescription(dto.getDescription().trim());
        med.setConcentration(dto.getConcentration().trim());
        med.setPresentation(dto.getPresentation().trim());

        return toResponse(medicineRepository.save(med));
    }

    // Eliminar (soft delete)
    public void delete(Long id) {
        Medicine med = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicina no encontrada"));

        med.setStatus(false);
        medicineRepository.save(med);
    }

    // Listar todo
    public List<MedicineResponseDTO> findAll() {
        return medicineRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Buscar por ID
    public MedicineResponseDTO findById(Long id) {
        return medicineRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Medicina no encontrada"));
    }

    // Buscar por nombre
    public MedicineResponseDTO findByName(String name) {
        return medicineRepository.findByName(name)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("No se encontró una medicina con ese nombre"));
    }

    // Mapper
    private MedicineResponseDTO toResponse(Medicine med) {
        MedicineResponseDTO dto = new MedicineResponseDTO();
        dto.setId(med.getId());
        dto.setName(med.getName());
        dto.setDescription(med.getDescription());
        dto.setConcentration(med.getConcentration());
        dto.setPresentation(med.getPresentation());
        dto.setStatus(med.getStatus());
        return dto;
    }
}
