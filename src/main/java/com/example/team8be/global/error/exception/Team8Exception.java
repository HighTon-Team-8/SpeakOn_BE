package com.example.team8be.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Team8Exception extends RuntimeException {
    private final ErrorCode errorCode;
}
