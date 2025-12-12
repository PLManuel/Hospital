package com.hospital.analysis_cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.analysis_cart.model.AnalysisCartItem;
import com.hospital.analysis_cart.repository.AnalysisCartItemRepository;


@Service
public class AnalysisCartService {
    @Autowired
    private AnalysisCartItemRepository repo;

    public AnalysisCartItem agregar(AnalysisCartItem item) {
        return repo.save(item);
    }

    public void quitar(Long id) {
        repo.deleteById(id);
    }

    public List<AnalysisCartItem> listar() {
        return repo.findAll();
    }

    public void nuevo() {
        repo.deleteAll();
    }
}
