package com.example.team8be.domain.presentation.service;

import com.example.team8be.domain.presentation.domain.Presentation;
import com.example.team8be.domain.presentation.domain.repository.PresentationRepository;
import com.example.team8be.infrastructure.openai.service.GPTService;
import com.example.team8be.infrastructure.openai.service.WhisperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadPresentationService {
    private final WhisperService whisperService;
    private final GPTService gptService;
    private final PresentationRepository presentationRepository;

    public Presentation execute(MultipartFile file) {
        try {
            // 파일 임시 저장
            File tempFile = File.createTempFile("upload", ".wav");
            file.transferTo(tempFile);

            // Whisper STT → transcript 생성
            String transcript = whisperService.transcribeAudio(tempFile);

            // summary, deliveryScore & feedback 생성
            Map<String, Object> analysis = gptService.analyzePresentationFull(transcript);
            String summary = (String) analysis.get("summary");
            int deliveryScore = (int) analysis.get("deliveryScore");
            String feedback = (String) analysis.get("feedback");

            return presentationRepository.save(Presentation.builder()
                    .file_url(tempFile.getAbsolutePath())
                    .summary(summary)
                    .deliveryScore(deliveryScore)
                    .feedback(feedback)
                    .build());

        } catch (IOException e) {
            throw new RuntimeException("파일 처리 중 오류 발생", e);
        }
    }
}
