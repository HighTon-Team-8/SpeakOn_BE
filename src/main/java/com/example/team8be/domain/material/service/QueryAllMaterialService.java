package com.example.team8be.domain.material.service;

import com.example.team8be.domain.material.domain.repository.MaterialRepository;
import com.example.team8be.domain.material.presentation.dto.response.MaterialListResponse;
import com.example.team8be.domain.user.domain.User;
import com.example.team8be.domain.user.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllMaterialService {
    private final MaterialRepository materialRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<MaterialListResponse> execute() {
        User user = userFacade.currentUser();

        return materialRepository.findAll()
                .stream()
                .map(MaterialListResponse::new)
                .toList();
    }
}
