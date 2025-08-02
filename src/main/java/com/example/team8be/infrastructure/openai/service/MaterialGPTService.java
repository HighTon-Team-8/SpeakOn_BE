package com.example.team8be.infrastructure.openai.service;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialGPTService {
    @Value("${openai.api.key}")
    private String openaiApiKey;

    public List<String> generateSlideScript(String documentText) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content",
                "다음 발표 자료를 기반으로 슬라이드별 발표 대본을 작성해 주세요.\n" +
                        "각 슬라이드는 2~3문장으로 요약한 발표 대본으로 작성하고, JSON 배열로만 출력해 주세요.\n\n"
                        + documentText
        );

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
            String content = new JSONObject(result)
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();

            // JSON 배열 파싱
            JSONArray array = new JSONArray(content);
            List<String> slides = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                slides.add(array.getString(i));
            }
            return slides;
        }
    }

}
