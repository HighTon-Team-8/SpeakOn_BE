package com.example.team8be.domain.presentation.domain.presentation.dto;

import com.example.team8be.domain.presentation.domain.Presentation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresentationResponse {
    private String file_url;

    private String summary;

    private int deliveryScore;

    private String feedback;

    public PresentationResponse(Presentation presentation) {
        this.file_url = presentation.getFile_url();
        this.summary = presentation.getSummary();
        this.deliveryScore = presentation.getDeliveryScore();
        this.feedback = presentation.getFeedback();
    }
}
