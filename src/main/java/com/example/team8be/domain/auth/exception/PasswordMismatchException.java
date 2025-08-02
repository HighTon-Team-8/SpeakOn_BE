package com.example.firststep_server.domain.auth.exception;

import com.example.firststep_server.global.error.exception.TurkeyException;
import com.example.firststep_server.global.error.exception.ErrorCode;

public class PasswordMismatchException extends TurkeyException {
    public static final TurkeyException EXCEPTION = new PasswordMismatchException();

    public PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
