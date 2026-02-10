package com.unburden.infrastructure.ai;

import com.unburden.infrastructure.ai.dto.ExtractedInsightResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DecisionAiAnalyzer {

    private final ChatClient chatClient;


    public ExtractedInsightResult analyze(final String originalText) {
        return chatClient.prompt()
                .system("""
                        당신은 사용자의 구매 고민을 분석하는 보조 도구입니다.
                        추천이나 판단을 하지 마세요.
                        반드시 아래 JSON 형식으로만 응답하세요.
                        
                        {
                          "aiSummary": string,
                          "concerns": string[],
                          "tags": string[]
                        }
                        """)
                .user(originalText)
                .call()
                .entity(ExtractedInsightResult.class);
    }

    private Prompt prompt(String text) {
        return new Prompt("""
                너는 사용자의 고민을 분석해 구조화하는 도우미다.
                판단하거나 추천하지 말고, 관찰한 내용만 정리한다.
                
                반드시 아래 JSON 형식으로만 응답하라.
                
                {
                    "summary": "고민 요약,
                    "concerns": ["주요 걱정"],
                    "tags": ["키워드"]
                }
                
                고민:
                %s
                """.formatted(text));
    }

}