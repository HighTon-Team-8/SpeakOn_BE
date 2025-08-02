package com.example.team8be.domain.presentation.service;

import com.example.team8be.domain.presentation.domain.presentation.dto.PresentationListResponse;
import com.example.team8be.domain.presentation.domain.presentation.dto.PresentationResponse;
import com.example.team8be.domain.presentation.domain.repository.PresentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllPresentationService {
    private final PresentationRepository presentationRepository;

    @Transactional(readOnly = true)
    public List<PresentationListResponse> execute() {
        return presentationRepository.findAll()
                .stream()
                .map(PresentationListResponse::new)
                .toList();
    }
}
