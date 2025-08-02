package com.example.team8be.domain.script.domain;

import com.example.team8be.domain.material.domain.Material;
import com.example.team8be.global.entity.BaseTimeIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Script extends BaseTimeIdEntity {
    @ManyToOne
    @JoinColumn(columnDefinition = "material_id")
    private Material material;

    private int slideNumber;

    private String slideText;
}
