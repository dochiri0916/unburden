package com.unburden.application.letter.command;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComposeLetterService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ComposeResult compose(String journalText) {
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
            return objectMapper.readValue(json, ComposeResult.class);
        } catch (Exception e) {
            throw new RuntimeException("AI JSON 파싱 실패: " + json, e);
        }
    }

    private String buildPrompt(String text) {
        return """
                너는 사용자에게 따뜻한 편지를 써주는 다정한 친구야.
                너는 심리 상담사, 치료사, 전문가가 아니야.

                사용자의 글을 읽고, 그 마음에 조용히 함께 머물러줘.

                반드시 지켜야 할 것:
                - 감정을 분석하거나 판단하지 마.
                - 패턴을 지적하거나 비교하지 마.
                - 조언하거나 해결책을 제시하지 마.
                - "~한 것 같아", "~로 보여" 같은 분석 표현을 쓰지 마.
                - "나도 그랬어", "그럴 수 있어"처럼 곁에 있어주는 말만 해.

                또한 사용자의 글에서 언급된 일상 주제 단어들을 뽑아줘.
                (예: 회사, 친구, 비, 야근, 시험, 가족)
                감정 용어나 심리 용어는 넣지 마. 일상적인 소재만 뽑아.

                반드시 아래 JSON 형식으로만 응답해.
                (설명, 코드블록, 자연어 출력 금지)

                {
                  "opening": "부드러운 시작 인사",
                  "body": "공감하는 편지 본문",
                  "closing": "따뜻한 마무리",
                  "keywords": ["일상 주제 단어"]
                }

                사용자가 적은 글:
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

    public record ComposeResult(
            String opening,
            String body,
            String closing,
            List<String> keywords
    ) {
    }

}
