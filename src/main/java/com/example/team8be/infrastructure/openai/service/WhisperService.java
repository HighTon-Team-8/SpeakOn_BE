package com.example.team8be.infrastructure.openai.service;

import jakarta.annotation.PostConstruct;
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
import java.nio.file.Files;
import java.util.Objects;
@Slf4j
@Service
@RequiredArgsConstructor
public class WhisperService {

    @Value("${chatgpt.api-key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    public String transcribeAudio(File audioFile) throws IOException {
        // 확장자에 따른 MIME Type 결정
        String mimeType = Files.probeContentType(audioFile.toPath());
        if (mimeType == null) mimeType = "audio/mpeg"; // fallback

        RequestBody fileBody = RequestBody.create(audioFile, MediaType.get(mimeType));

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
                throw new IOException("STT 실패: " + raw);
            }

            String text = new JSONObject(raw).getString("text");
            return text;
        }
    }

}
