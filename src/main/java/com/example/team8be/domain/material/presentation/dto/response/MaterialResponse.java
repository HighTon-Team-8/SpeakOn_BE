package com.example.team8be.domain.material.presentation.dto.response;

import com.example.team8be.domain.material.domain.Material;
import com.example.team8be.domain.script.domain.Script;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MaterialResponse {
    private Long id;
    private String title;
    private List<Script> script;
    private LocalDateTime createdAt;

    public MaterialResponse(Material material) {
        this.id = material.getId();
        this.title = material.getTitle();
        this.createdAt = material.getCreatedAt();
    }
}
