package com.example.team8be.domain.presentation.domain.presentation.dto;

import com.example.team8be.domain.presentation.domain.Presentation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresentationResponse {
    private String summary;

    private int deliveryScore;

    private String feedback;
}
