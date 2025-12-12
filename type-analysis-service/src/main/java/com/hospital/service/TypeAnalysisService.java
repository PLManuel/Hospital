package com.hospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hospital.dto.TypeAnalysisRequestDTO;
import com.hospital.dto.TypeAnalysisResponseDTO;
import com.hospital.model.TypeAnalysis;
import com.hospital.repository.TypeAnalysisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeAnalysisService {
    private final TypeAnalysisRepository analysisRepository;

    public TypeAnalysisResponseDTO create(TypeAnalysisRequestDTO dto) {

        // Validación: nombre duplicado
        analysisRepository.findByNameIgnoreCase(dto.getName()).ifPresent(t -> {
            throw new RuntimeException("Ya existe un tipo de análisis con ese nombre");
        });

        TypeAnalysis type = TypeAnalysis.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : true)
                .build();

        return toResponse(analysisRepository.save(type));
    }

    public TypeAnalysisResponseDTO update(Long id, TypeAnalysisRequestDTO dto) {

        TypeAnalysis type = analysisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de análisis no encontrado"));

        type.setName(dto.getName());
        type.setDescription(dto.getDescription());
        if (dto.getStatus() != null) {
            type.setStatus(dto.getStatus());
        }

        return toResponse(analysisRepository.save(type));
    }

    public void delete(Long id) {
        if (!analysisRepository.existsById(id)) {
            throw new RuntimeException("El tipo de análisis no existe");
        }
        analysisRepository.deleteById(id);
    }

    public List<TypeAnalysisResponseDTO> findAll() {
        return analysisRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TypeAnalysisResponseDTO findById(Long id) {
        return analysisRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Tipo de análisis no encontrado"));
    }

    private TypeAnalysisResponseDTO toResponse(TypeAnalysis t) {
        return TypeAnalysisResponseDTO.builder()
                .id(t.getId())
                .name(t.getName())
                .description(t.getDescription())
                .status(t.getStatus())
                .build();
    }
}
