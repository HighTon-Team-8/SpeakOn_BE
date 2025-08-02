package com.example.team8be.infrastructure.openai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PresentationGPTService {
    @Value("${chatgpt.api-key}")
    private String apiKey;

    @Value("${chatgpt.model:gpt-4o-mini}")
    private String model;

    @Value("${chatgpt.temperature:0.7}")
    private double temperature;

    @Value("${chatgpt.maxTokens:2048}")
    private int maxTokens;

    private final OkHttpClient client = new OkHttpClient();

    public Map<String, Object> analyzePresentationFull(String transcript) throws IOException {
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content",
                "당신은 발표 전문가입니다. 다음 발표 대본을 분석하여 JSON으로 반환하세요.\n\n" +
                        "대본:\n\"" + transcript + "\"\n\n" +
                        "JSON 형식 예시:\n{\n" +
                        "  \"summary\": \"자세한 발표 요약\",\n" +
                        "  \"deliveryScore\": 1~5 사이 정수,\n" +
                        "  \"feedback\": \"발표에 대한 구체적 피드백\"\n" +
                        "}\n" +
                        "JSON만 반환하세요."
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
            String raw = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                log.error("❌ GPT 요청 실패 - 상태코드: {}, 응답: {}", response.code(), raw);
                throw new IOException("GPT 요청 실패");
            }

            // GPT 응답에서 content 추출
            String content = new JSONObject(raw)
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();

            content = content.replaceAll("^```json", "")
                    .replaceAll("```$", "")
                    .trim();

            // JSON 파싱
            JSONObject json = new JSONObject(content);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("summary", json.optString("summary", ""));
            resultMap.put("deliveryScore", json.optInt("deliveryScore", 0));
            resultMap.put("feedback", json.optString("feedback", ""));

            return resultMap;
        }
    }

}
