package com.example.team8be.domain.presentation.exception;

import com.example.team8be.global.error.exception.ErrorCode;
import com.example.team8be.global.error.exception.Team8Exception;

public class PresentationNotFoundException extends Team8Exception {
    public static final Team8Exception EXCEPTION = new PresentationNotFoundException();

    private PresentationNotFoundException() {
        super(ErrorCode.PRESENTATION_NOT_FOUND);
    }
}
