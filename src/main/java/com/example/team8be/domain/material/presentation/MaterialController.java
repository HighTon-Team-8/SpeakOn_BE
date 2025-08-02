package com.example.team8be.domain.material.presentation;

import com.example.team8be.domain.material.domain.Material;
import com.example.team8be.domain.material.presentation.dto.request.MaterialRequest;
import com.example.team8be.domain.material.presentation.dto.response.MaterialResponse;
import com.example.team8be.domain.material.service.QueryMaterialService;
import com.example.team8be.domain.material.service.UploadMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialController {
    private final UploadMaterialService uploadMaterialService;
    private final QueryMaterialService queryMaterialService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload")
    public Material uploadMaterial(@RequestPart(name = "file") MultipartFile file,
                                   @RequestPart(name = "request") @Valid MaterialRequest request) {
        return uploadMaterialService.execute(file, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public MaterialResponse queryMaterial(@PathVariable Long id) {
        return queryMaterialService.execute(id);
    }
}
