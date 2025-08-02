package com.example.team8be.domain.presentation.service;

import com.example.team8be.domain.presentation.domain.Presentation;
import com.example.team8be.domain.presentation.domain.presentation.dto.PresentationResponse;
import com.example.team8be.domain.presentation.domain.repository.PresentationRepository;
import com.example.team8be.domain.presentation.exception.PresentationNotFoundException;
import com.example.team8be.domain.user.domain.User;
import com.example.team8be.domain.user.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPresentationService {
    private final PresentationRepository presentationRepository;
    private final UserFacade userFacade;

    public PresentationResponse execute(Long id) {
        User user = userFacade.currentUser();

        Presentation presentation = presentationRepository.findById(id)
                .orElseThrow(() -> PresentationNotFoundException.EXCEPTION);

        return PresentationResponse.builder()
                .summary(presentation.getSummary())
                .deliveryScore(presentation.getDeliveryScore())
                .feedback(presentation.getFeedback())
                .build();
    }
}
