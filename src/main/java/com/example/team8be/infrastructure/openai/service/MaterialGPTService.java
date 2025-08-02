package com.example.team8be.infrastructure.openai.service;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MaterialGPTService {
    @Value("${chatgpt.api-key}")
    private String apiKey;

    @Value("${chatgpt.model}")
    private String model;

    @Value("${chatgpt.temperature}")
    private double temperature;

    @Value("${chatgpt.maxTokens}")
    private int maxTokens;

    public String generateSlideScript(String slideText) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content",
                "다음 슬라이드 내용을 2~3문장으로 요약하여 발표자가 읽을 대본으로 만들어 주세요.\n" +
                        "대본만 반환하세요.\n\n" + slideText
        );

        JSONObject requestJson = new JSONObject();
        requestJson.put("model", model);
        requestJson.put("temperature", temperature);
        requestJson.put("max_tokens", maxTokens);
        requestJson.put("messages", new JSONArray().put(message));

        RequestBody body = RequestBody.create(
                requestJson.toString(),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String rawResponse = response.body().string();
            if (!response.isSuccessful()) {
                System.err.println("GPT 요청 실패 - 코드: " + response.code() + ", 응답: " + rawResponse);
                throw new IOException("GPT 요청 실패");
            }

            String content = new JSONObject(rawResponse)
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();

            return content;
        }
    }

}
