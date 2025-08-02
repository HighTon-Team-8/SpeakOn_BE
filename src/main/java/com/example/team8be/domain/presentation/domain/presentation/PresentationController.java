package com.example.team8be.domain.presentation.domain.presentation;

import com.example.team8be.domain.presentation.domain.Presentation;
import com.example.team8be.domain.presentation.service.UploadPresentationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/presentation")
@RequiredArgsConstructor
public class PresentationController {
    private final UploadPresentationService presentationUploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload")
    public Presentation upload(@RequestPart(value = "file") MultipartFile file) {
        return presentationUploadService.execute(file);
    }
}
