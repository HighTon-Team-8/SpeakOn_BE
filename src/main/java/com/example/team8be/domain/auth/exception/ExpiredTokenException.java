package com.example.team8be.domain.auth.exception;

import com.example.team8be.global.error.exception.ErrorCode;
import com.example.team8be.global.error.exception.Team8Exception;

public class ExpiredTokenException extends Team8Exception {
    public static final Team8Exception EXCEPTION = new ExpiredTokenException();

    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
