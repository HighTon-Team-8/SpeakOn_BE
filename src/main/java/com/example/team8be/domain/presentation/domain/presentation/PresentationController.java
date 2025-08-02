package com.example.team8be.domain.presentation.domain.presentation;

import com.example.team8be.domain.presentation.domain.Presentation;
import com.example.team8be.domain.presentation.domain.presentation.dto.PresentationListResponse;
import com.example.team8be.domain.presentation.domain.presentation.dto.PresentationResponse;
import com.example.team8be.domain.presentation.service.QueryAllPresentationService;
import com.example.team8be.domain.presentation.service.QueryPresentationService;
import com.example.team8be.domain.presentation.service.UploadPresentationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/presentation")
@RequiredArgsConstructor
public class PresentationController {
    private final UploadPresentationService presentationUploadService;
    private final QueryPresentationService queryPresentationService;
    private final QueryAllPresentationService queryAllPresentationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload")
    public Presentation upload(@RequestPart(value = "file") MultipartFile file) {
        return presentationUploadService.execute(file);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public PresentationResponse queryPresentation(@PathVariable Long id) {
        return queryPresentationService.execute(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PresentationListResponse> queryAllPresentation() {
        return queryAllPresentationService.execute();
    }
}
