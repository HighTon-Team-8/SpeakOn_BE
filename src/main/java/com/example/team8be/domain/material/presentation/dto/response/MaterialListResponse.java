package com.example.team8be.domain.material.presentation.dto.response;

import com.example.team8be.domain.material.domain.Material;
import com.example.team8be.domain.script.domain.Script;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MaterialListResponse {
    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public MaterialListResponse(Material material) {
        this.id = material.getId();
        this.title = material.getTitle();
        this.createdAt = material.getCreatedAt();
    }

}
