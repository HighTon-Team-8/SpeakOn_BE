package com.example.team8be.domain.material.service;

import com.example.team8be.domain.material.domain.repository.MaterialRepository;
import com.example.team8be.domain.material.presentation.dto.response.MaterialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllMaterialService {
    private final MaterialRepository materialRepository;

    @Transactional(readOnly = true)
    public List<MaterialResponse> execute() {
        return materialRepository.findAll()
                .stream()
                .map(MaterialResponse::new)
                .toList();
    }
}
