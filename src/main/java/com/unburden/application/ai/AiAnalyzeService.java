package com.unburden.application.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiAnalyzeService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Result analyze(String journalText) {
        String raw = chatClient
                .prompt()
                .user(buildPrompt(journalText))
                .call()
                .content();

        if (raw == null || raw.isBlank()) {
            throw new IllegalStateException("AI 응답이 비어 있습니다.");
        }

        String json = extractJson(raw);

        try {
            return objectMapper.readValue(json, Result.class);
        } catch (Exception e) {
            throw new RuntimeException("AI JSON 파싱 실패: " + json, e);
        }
    }

    private String buildPrompt(String text) {
        return """
                너는 사용자의 감정을 판단하지 않고 조용히 공감해주는 편지를 쓰는 존재다.
                
                조언하지 말고, 해결책을 제시하지 말고,
                사용자의 감정을 그대로 받아들이고 함께 머물러라.
                
                반드시 아래 JSON 형식으로만 응답하라.
                (설명, 코드블록, 자연어 출력 금지)
                
                {
                  "opening": "부드러운 시작 문장",
                  "body": "사용자의 감정에 공감하는 편지 본문",
                  "closing": "따뜻한 마무리 문장"
                }
                
                오늘 사용자가 적은 글:
                %s
                """.formatted(text);
    }

    private String extractJson(String raw) {
        int start = raw.indexOf('{');
        int end = raw.lastIndexOf('}');
        if (start == -1 || end == -1 || start >= end) {
            throw new RuntimeException("유효한 JSON을 찾지 못함: " + raw);
        }
        return raw.substring(start, end + 1);
    }

    public record Result(
            String opening,
            String body,
            String closing
    ) {
    }

}