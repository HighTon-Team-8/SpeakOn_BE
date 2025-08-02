package com.example.team8be.domain.auth.service;

import com.example.team8be.domain.auth.presentation.dto.request.SignupRequest;
import com.example.team8be.domain.user.domain.User;
import com.example.team8be.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(SignupRequest request) {
        userRepository.save(User.builder()
                .userName(request.getUserName())
                .accountId(request.getAccountId())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
    }
}
