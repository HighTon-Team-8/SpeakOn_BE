package com.example.team8be.domain.material.service;

import com.example.team8be.domain.material.domain.Material;
import com.example.team8be.domain.material.domain.repository.MaterialRepository;
import com.example.team8be.domain.material.exception.MaterialNotFoundException;
import com.example.team8be.domain.material.presentation.dto.response.MaterialResponse;
import com.example.team8be.domain.script.domain.Script;
import com.example.team8be.domain.script.domain.repository.ScriptRepository;
import com.example.team8be.domain.user.domain.User;
import com.example.team8be.domain.user.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryMaterialService {
    private final MaterialRepository materialRepository;
    private final ScriptRepository scriptRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public MaterialResponse execute(Long materialId) {
        User user = userFacade.currentUser();

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> MaterialNotFoundException.EXCEPTION);

        List<Script> scripts = scriptRepository.findByMaterialIdOrderBySlideNumberAsc(materialId);

        return MaterialResponse.builder()
                .id(material.getId())
                .title(material.getTitle())
                .script(scripts)
                .createdAt(material.getCreatedAt())
                .build();

    }
}
