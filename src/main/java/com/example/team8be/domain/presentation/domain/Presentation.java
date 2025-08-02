package com.example.team8be.domain.presentation.domain;

import com.example.team8be.global.entity.BaseTimeIdEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Presentation extends BaseTimeIdEntity {
    private String file_url;

    private String transcript;

    private int deliveryScore;

    private String feedback;
}
