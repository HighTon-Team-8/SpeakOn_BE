package com.example.team8be.infrastructure.openai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhisperService {

    @Value("${chatgpt.api-key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    public String transcribeAudio(File audioFile) throws IOException {
        // 확장자 검증
        if (!audioFile.getName().toLowerCase().endsWith(".wav")) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다. WAV 파일만 업로드하세요.");
        }

        // MIME 타입은 WAV로 고정
        MediaType mediaType = MediaType.get("audio/wav");
        RequestBody fileBody = RequestBody.create(audioFile, mediaType);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", audioFile.getName(), fileBody)
                .addFormDataPart("model", "whisper-1")
                .build();

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/audio/transcriptions")
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String raw = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                log.error("❌ Whisper API 요청 실패 - 상태코드: {}, 응답: {}", response.code(), raw);
                throw new IOException("STT 실패: " + raw);
            }

            return new JSONObject(raw).getString("text");
        }
    }

}
