package com.example.team8be.domain.auth.presentation;

import com.example.team8be.domain.auth.presentation.dto.request.LoginRequest;
import com.example.team8be.domain.auth.presentation.dto.request.RefreshTokenRequest;
import com.example.team8be.domain.auth.presentation.dto.request.SignupRequest;
import com.example.team8be.domain.auth.presentation.dto.response.TokenResponse;
import com.example.team8be.domain.auth.service.LoginService;
import com.example.team8be.domain.auth.service.LogoutService;
import com.example.team8be.domain.auth.service.ReissueService;
import com.example.team8be.domain.auth.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SignupService signupService;
    private final LoginService loginService;
    private final ReissueService reissueService;
    private final LogoutService logoutService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signup(@RequestBody @Valid SignupRequest request) {
        signupService.execute(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/re-issue")
    public TokenResponse reissue(@RequestBody @Valid RefreshTokenRequest request) {
        return reissueService.execute(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/logout")
    public void logout() {
        logoutService.execute();
    }
}
