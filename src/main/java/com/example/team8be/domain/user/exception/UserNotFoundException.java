package com.example.firststep_server.domain.user.exception;

import com.example.firststep_server.global.error.exception.ErrorCode;
import com.example.firststep_server.global.error.exception.TurkeyException;

public class UserNotFoundException extends TurkeyException {
    public static final TurkeyException EXCEPTION = new UserNotFoundException();

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
