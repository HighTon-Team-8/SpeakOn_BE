package com.example.team8be.domain.presentation.service;

import com.example.team8be.domain.presentation.domain.Presentation;
import com.example.team8be.domain.presentation.domain.presentation.dto.PresentationResponse;
import com.example.team8be.domain.presentation.domain.repository.PresentationRepository;
import com.example.team8be.domain.presentation.exception.PresentationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPresentationService {
    private final PresentationRepository presentationRepository;

    public PresentationResponse execute(Long presentationId) {
        Presentation presentation = presentationRepository.findById(presentationId)
                .orElseThrow(() -> PresentationNotFoundException.EXCEPTION);

        return PresentationResponse.builder()
                .file_url(presentation.getFile_url())
                .summary(presentation.getSummary())
                .deliveryScore(presentation.getDeliveryScore())
                .feedback(presentation.getFeedback())
                .build();
    }
}
