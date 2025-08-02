package com.example.team8be.domain.auth.exception;

import com.example.team8be.global.error.exception.ErrorCode;
import com.example.team8be.global.error.exception.Team8Exception;

public class InvalidTokenException extends Team8Exception {
    public static final Team8Exception EXCEPTION = new InvalidTokenException();

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
