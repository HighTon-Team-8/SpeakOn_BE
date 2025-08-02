package com.example.team8be.domain.user.service;

import com.example.team8be.domain.user.domain.User;
import com.example.team8be.domain.user.domain.repository.UserRepository;
import com.example.team8be.domain.user.presentation.dto.response.MyInfoResponse;
import com.example.team8be.domain.user.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryMyInfoService {
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public MyInfoResponse execute() {
        User user = userFacade.currentUser();

        return MyInfoResponse.builder()
                .userName(user.getUserName())
                .introduction(user.getIntroduction())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
