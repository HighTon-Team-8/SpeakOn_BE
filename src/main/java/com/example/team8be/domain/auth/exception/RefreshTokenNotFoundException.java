package com.example.team8be.domain.auth.exception;

import com.example.team8be.global.error.exception.ErrorCode;
import com.example.team8be.global.error.exception.Team8Exception;

public class RefreshTokenNotFoundException extends Team8Exception {
    public static final Team8Exception EXCEPTION = new RefreshTokenNotFoundException();

    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
