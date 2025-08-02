package com.example.team8be.domain.auth.service;

import com.example.team8be.domain.auth.domain.RefreshToken;
import com.example.team8be.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.team8be.domain.auth.exception.RefreshTokenNotFoundException;
import com.example.team8be.domain.user.domain.User;
import com.example.team8be.domain.user.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserFacade userFacade;

    public void execute() {
        User user = userFacade.currentUser();

        RefreshToken refreshToken = refreshTokenRepository.findById(user.getAccountId())
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        refreshTokenRepository.delete(refreshToken);

    }
}
