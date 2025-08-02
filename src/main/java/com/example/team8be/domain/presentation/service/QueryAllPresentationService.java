package com.example.team8be.domain.presentation.service;

import com.example.team8be.domain.presentation.domain.presentation.dto.PresentationListResponse;
import com.example.team8be.domain.presentation.domain.presentation.dto.PresentationResponse;
import com.example.team8be.domain.presentation.domain.repository.PresentationRepository;
import com.example.team8be.domain.user.domain.User;
import com.example.team8be.domain.user.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllPresentationService {
    private final PresentationRepository presentationRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<PresentationListResponse> execute() {
        User user = userFacade.currentUser();

        return presentationRepository.findAll()
                .stream()
                .map(PresentationListResponse::new)
                .toList();
    }
}
