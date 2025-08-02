package com.example.firststep_server.domain.auth.service;

import com.example.firststep_server.domain.auth.domain.RefreshToken;
import com.example.firststep_server.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.firststep_server.domain.auth.exception.RefreshTokenNotFoundException;
import com.example.firststep_server.domain.user.domain.User;
import com.example.firststep_server.domain.user.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserFacade userFacade;

    public void logout() {
        User user = userFacade.currentUser();

        RefreshToken refreshToken = refreshTokenRepository.findById(user.getAccountId())
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        refreshTokenRepository.delete(refreshToken);

    }
}
