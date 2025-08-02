package com.example.team8be.infrastructure.openai.service;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GPTService {
    @Value("${openai.api.key}")
    private String openaiApiKey;

    public Map<String, Object> analyzePresentationFull(String transcript) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content",
                "당신은 발표 전문가입니다. 다음 발표 대본을 분석하여 JSON으로 반환하세요.\n\n" +
                        "대본:\n\"" + transcript + "\"\n\n" +
                        "JSON 형식:\n{\n" +
                        "  \"summary\": \"자세한 발표 요약\",\n" +
                        "  \"deliveryScore\": 1~5 사이 정수,\n" +
                        "  \"feedback\": \"발표에 대한 구체적 피드백\"\n" +
                        "}");

        JSONObject requestJson = new JSONObject();
        requestJson.put("model", "gpt-4o-mini");
        requestJson.put("messages", new JSONArray().put(message));

        RequestBody body = RequestBody.create(
                requestJson.toString(),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + openaiApiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("GPT 요청 실패");

            String result = response.body().string();
            JSONObject choice = new JSONObject(result)
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message");

            String content = choice.getString("content").trim();

            // GPT가 준 JSON 문자열 파싱
            JSONObject json = new JSONObject(content);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("summary", json.getString("summary"));
            resultMap.put("deliveryScore", json.getInt("deliveryScore"));
            resultMap.put("feedback", json.getString("feedback"));

            return resultMap;
        }
    }

}
