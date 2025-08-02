package com.example.team8be.domain.presentation.domain.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresentationResponse {
    private String file_url;

    private String transcript;

    private int deliveryScore;

    private String feedback;
}
