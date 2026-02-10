package com.unburden.application.journal.command;

import com.unburden.application.journal.event.JournalWrittenEvent;
import com.unburden.domain.journal.DailyJournalLimitExceededException;
import com.unburden.domain.journal.Journal;
import com.unburden.infrastructure.messaging.rabbitmq.JournalEventPublisher;
import com.unburden.infrastructure.persistence.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WriteJournalService {

    private final JournalRepository journalRepository;
    private final JournalEventPublisher journalEventPublisher;

    @Transactional
    public void write(final Long userId, final String journalText) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        boolean alreadyWritten = journalRepository.existsByUserIdAndCreatedAtBetweenAndDeletedAtIsNull(
                userId, start, end
        );

        if (alreadyWritten) {
            //TODO: 예외 메시지 예외 안에서 정의
            throw new DailyJournalLimitExceededException("하루에 하나의 일기만 작성할 수 있습니다.");
        }

        Journal journal = journalRepository.save(Journal.write(userId, journalText));

        journalEventPublisher.publish(
                new JournalWrittenEvent(
                        journal.getUserId(),
                        journal.getId(),
                        journal.getCreatedAt()
                )
        );
    }

}