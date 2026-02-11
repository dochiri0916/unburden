package com.unburden.application.letter.command;

import com.unburden.application.journal.query.JournalLoader;
import com.unburden.application.letter.dto.ComposeResult;
import com.unburden.domain.journal.Journal;
import com.unburden.domain.letter.Letter;
import com.unburden.domain.letter.LetterGenerationException;
import com.unburden.infrastructure.persistence.LetterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComposeLetterService {

    private final JournalLoader journalLoader;
    private final LetterRepository letterRepository;
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void composeFromJournal(final Long journalId) {
        Journal journal = journalLoader.loadById(journalId);

        if (letterRepository.existsByJournalId(journalId)) {
            return;
        }

        String content = composeContent(journal.getContent());

        log.info("content={}", content);

        Letter letter = Letter.create(journal.getUserId(), journal.getId(), content);
        letterRepository.save(letter);
        journal.markProcessed();
    }

    private String composeContent(String journalText) {
        String raw = chatClient
                .prompt()
                .user(buildPrompt(journalText))
                .call()
                .content();

        if (raw == null || raw.isBlank()) {
            throw new LetterGenerationException();
        }

        String json = extractJson(raw);

        try {
            ComposeResult result = objectMapper.readValue(json, ComposeResult.class);
            return merge(result);
        } catch (Exception e) {
            throw new LetterGenerationException(e);
        }
    }

    private String merge(final ComposeResult result) {
        return String.join(
                "\n\n",
                result.opening(),
                result.body(),
                result.closing()
        );
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
            throw new LetterGenerationException();
        }
        return raw.substring(start, end + 1);
    }

}
