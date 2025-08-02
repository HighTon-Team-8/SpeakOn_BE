package com.example.team8be.domain.material.domain;

import com.example.team8be.global.entity.BaseTimeIdEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Material extends BaseTimeIdEntity {
    private String title;
    private String fileUrl;
}
