package com.unburden.infrastructure.rabbitmq;

import com.unburden.application.journal.event.JournalWrittenEvent;
import com.unburden.application.letter.command.ComposeLetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JournalWrittenEventConsumer {

    private final ComposeLetterService composeLetterService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consume(JournalWrittenEvent event) {
        try {
            composeLetterService.composeFromJournal(event.journalId());
        } catch (Exception e) {
            log.error(
                    "편지 생성 실패 - journalId={}",
                    event.journalId(),
                    e
            );
        }
    }

}