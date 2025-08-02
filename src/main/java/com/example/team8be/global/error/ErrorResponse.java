package com.example.team8be.global.error;

import com.example.team8be.global.error.exception.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Integer status;
    private LocalDateTime timestamp;
    private String description;

    public static ErrorResponse of(ErrorCode errorCode, String description) {
        return ErrorResponse.builder()
                .message(errorCode.getErrorMessage())
                .status(errorCode.getStatusCode())
                .timestamp(LocalDateTime.now())
                .description(description)
                .build();
    }

    public static ErrorResponse of(int statusCode, String description) {
        return ErrorResponse.builder()
                .message(description)
                .status(statusCode)
                .timestamp(LocalDateTime.now())
                .description(description)
                .build();
    }

}
