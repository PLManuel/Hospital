package com.hospital.analysis_cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.analysis_cart.client.TypeAnalysisServiceClient;
import com.hospital.analysis_cart.dto.AnalysisCartItemRequestDTO;
import com.hospital.analysis_cart.dto.AnalysisCartItemResponseDTO;
import com.hospital.analysis_cart.dto.TypeAnalysisDTO;
import com.hospital.analysis_cart.model.AnalysisCartItem;
import com.hospital.analysis_cart.repository.AnalysisCartItemRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AnalysisCartService {
    private final AnalysisCartItemRepository repo;
    private final TypeAnalysisServiceClient typeAnalysisClient;


    public AnalysisCartItemResponseDTO agregar(AnalysisCartItemRequestDTO dto) {

        // Validaciones mínimas y necesarias
        if (dto.getTypeAnalysisId() == null || dto.getTypeAnalysisId() <= 0) {
            throw new IllegalArgumentException("El typeAnalysisId es obligatorio.");
        }

        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            dto.setQuantity(1);
        }

        AnalysisCartItem item = AnalysisCartItem.builder()
                .typeAnalysisId(dto.getTypeAnalysisId())
                .observations(dto.getObservations())
                .quantity(dto.getQuantity())
                .build();

        AnalysisCartItem saved = repo.save(item);

        TypeAnalysisDTO typeData =
                typeAnalysisClient.findById(saved.getTypeAnalysisId());

        return AnalysisCartItemResponseDTO.builder()
                .id(saved.getId())
                .typeAnalysis(typeData)
                .observations(saved.getObservations())
                .quantity(saved.getQuantity())
                .build();
    }

    public void quitar(Long id) {
        repo.deleteById(id);
    }

    public List<AnalysisCartItemResponseDTO> listar() {

        return repo.findAll()
                .stream()
                .map(item -> {
                    TypeAnalysisDTO typeData =
                            typeAnalysisClient.findById(item.getTypeAnalysisId());

                    return AnalysisCartItemResponseDTO.builder()
                            .id(item.getId())
                            .typeAnalysis(typeData)
                            .observations(item.getObservations())
                            .quantity(item.getQuantity())
                            .build();
                })
                .toList();
    }

    public void nuevo() {
        repo.deleteAll();
    }
}
