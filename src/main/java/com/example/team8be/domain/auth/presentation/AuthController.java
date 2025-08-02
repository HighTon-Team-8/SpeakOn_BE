package com.example.firststep_server.domain.auth.presentation;

import com.example.firststep_server.domain.auth.service.LoginService;
import com.example.firststep_server.domain.auth.service.LogoutService;
import com.example.firststep_server.domain.auth.service.ReissueService;
import com.example.firststep_server.domain.auth.service.SignupService;
import com.example.firststep_server.domain.auth.presentation.dto.request.LoginRequest;
import com.example.firststep_server.domain.auth.presentation.dto.request.RefreshTokenRequest;
import com.example.firststep_server.domain.auth.presentation.dto.request.SignupRequest;
import com.example.firststep_server.domain.auth.presentation.dto.response.TokenResponse;
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
        signupService.signup(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/re-issue")
    public TokenResponse reissue(@RequestBody @Valid RefreshTokenRequest request) {
        return reissueService.reissue(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/logout")
    public void logout() {
        logoutService.logout();
    }
}
