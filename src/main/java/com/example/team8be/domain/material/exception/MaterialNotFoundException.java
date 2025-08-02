package com.example.team8be.domain.material.exception;

import com.example.team8be.global.error.exception.ErrorCode;
import com.example.team8be.global.error.exception.Team8Exception;

public class MaterialNotFoundException extends Team8Exception {
    public static final Team8Exception EXCEPTION = new MaterialNotFoundException();

    private MaterialNotFoundException() {
        super(ErrorCode.MATERIAL_NOT_FOUND);
    }
}
