package com.example.firststep_server.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TurkeyException extends RuntimeException {
    private final ErrorCode errorCode;
}
