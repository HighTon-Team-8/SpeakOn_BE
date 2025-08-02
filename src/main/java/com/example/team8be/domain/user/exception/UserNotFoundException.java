package com.example.team8be.domain.user.exception;

import com.example.team8be.global.error.exception.ErrorCode;
import com.example.team8be.global.error.exception.Team8Exception;

public class UserNotFoundException extends Team8Exception {
    public static final Team8Exception EXCEPTION = new UserNotFoundException();

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
