package com.example.team8be.domain.presentation.domain.presentation.dto;

import com.example.team8be.domain.presentation.domain.Presentation;
import lombok.Getter;

@Getter
public class PresentationListResponse {

    private String summary;
    private int deliveryScore;
    private String feedback;

    public PresentationListResponse(Presentation presentation) {
        this.summary = presentation.getSummary();
        this.deliveryScore = presentation.getDeliveryScore();
        this.feedback = presentation.getFeedback();
    }
}
