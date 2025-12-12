package com.hospital.analysis_sheet_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.analysis_sheet_service.model.AnalysisSheet;
import com.hospital.analysis_sheet_service.model.AnalysisSheetLine;
import com.hospital.analysis_sheet_service.service.AnalysisSheetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/analysis-sheets")
@RequiredArgsConstructor
public class AnalysisSheetController {
    @Autowired
    private AnalysisSheetService service;

    @PostMapping
    public AnalysisSheet crear(@RequestBody AnalysisSheet sheet) {
        return service.crear(sheet);
    }

    @GetMapping("/{id}")
    public AnalysisSheet obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @GetMapping("/por-atencion/{attentionId}")
    public List<AnalysisSheet> listarPorAtencion(@PathVariable Long attentionId) {
        return service.listarPorAtencion(attentionId);
    }

    @PostMapping("/{id}/lines")
    public AnalysisSheetLine agregarLinea(@PathVariable Long id, @RequestBody AnalysisSheetLine line) {
        line.setAnalysisSheetId(id);
        return service.agregarLinea(line);
    }

    @DeleteMapping("/lines/{lineId}")
    public void eliminarLinea(@PathVariable Long lineId) {
        service.eliminarLinea(lineId);
    }

    @PostMapping("/{id}/confirm")
    public void confirmar(@PathVariable Long id) {
        service.confirmar(id);
    }
}
