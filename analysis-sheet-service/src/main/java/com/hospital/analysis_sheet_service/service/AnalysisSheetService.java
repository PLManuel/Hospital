package com.hospital.analysis_sheet_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.analysis_sheet_service.client.AnalysisCartServiceClient;
import com.hospital.analysis_sheet_service.client.MedicalAttentionServiceClient;
import com.hospital.analysis_sheet_service.dto.AnalysisCartItemDTO;
import com.hospital.analysis_sheet_service.dto.MedicalAttentionDTO;
import com.hospital.analysis_sheet_service.model.AnalysisSheet;
import com.hospital.analysis_sheet_service.model.AnalysisSheetLine;
import com.hospital.analysis_sheet_service.repository.AnalysisSheetLineRepository;
import com.hospital.analysis_sheet_service.repository.AnalysisSheetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalysisSheetService {
    private final AnalysisSheetRepository sheetRepo;
    private final AnalysisSheetLineRepository lineRepo;
    private final AnalysisCartServiceClient cartClient;
    private final MedicalAttentionServiceClient attentionClient;

    // Crear ficha vacía
    public AnalysisSheet crear(AnalysisSheet sheet) {
        // Validar existencia de la atención
        try {
            MedicalAttentionDTO att = attentionClient.getAttentionByIdSimple(sheet.getAttentionId());
            if (att == null) {
                return null; 
            }
        } catch (Exception e) {
            return null;
        }
        sheet.setCreatedAt(LocalDateTime.now());
        sheet.setStatus("Pending");
        return sheetRepo.save(sheet);
    }

    // Obtener ficha
    public AnalysisSheet obtener(Long id) {
        return sheetRepo.findById(id).orElse(null);
    }

    // Listar fichas por atención médica
    public List<AnalysisSheet> listarPorAtencion(Long attentionId) {
        return sheetRepo.findByAttentionId(attentionId);
    }

    // Agregar línea manual (sin carrito)
    public AnalysisSheetLine agregarLinea(AnalysisSheetLine line) {
        return lineRepo.save(line);
    }

    // Eliminar línea
    public void eliminarLinea(Long lineId) {
        lineRepo.deleteById(lineId);
    }

    // Confirmar ficha = copiar líneas desde el carrito
    public void confirmar(Long sheetId) {
        AnalysisSheet sheet = sheetRepo.findById(sheetId).orElse(null);
        if (sheet == null) return;

        List<AnalysisCartItemDTO> cart = cartClient.getCartItems();

        for (AnalysisCartItemDTO item : cart) {
            AnalysisSheetLine line = new AnalysisSheetLine();

            line.setAnalysisSheetId(sheetId);
            line.setTypeAnalysisId(item.getTypeAnalysisId());
            line.setObservations(item.getObservations());
            line.setQuantity(item.getQuantity());

            lineRepo.save(line);
        }

        cartClient.limpiarCarrito();

        sheet.setStatus("Completed");
        sheetRepo.save(sheet);
    }
}
