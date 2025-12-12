package com.hospital.analysis_cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.analysis_cart.model.AnalysisCartItem;
import com.hospital.analysis_cart.service.AnalysisCartService;

@RestController
@RequestMapping("/analysis-cart")
public class AnalysisCartController {
    @Autowired
    private AnalysisCartService service;

    @PostMapping("/agregar")
    public AnalysisCartItem agregar(@RequestBody AnalysisCartItem item) {
        return service.agregar(item);
    }

    @DeleteMapping("/quitar/{id}")
    public void quitar(@PathVariable Long id) {
        service.quitar(id);
    }

    @GetMapping("/listar")
    public List<AnalysisCartItem> listar() {
        return service.listar();
    }

    @DeleteMapping("/nuevo")
    public void nuevo() {
        service.nuevo();
    }
}
