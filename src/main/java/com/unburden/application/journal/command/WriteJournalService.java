package com.unburden.application.journal.command;

import com.unburden.application.journal.event.JournalWrittenEvent;
import com.unburden.domain.journal.DailyJournalLimitExceededException;
import com.unburden.domain.journal.Journal;
import com.unburden.infrastructure.persistence.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WriteJournalService {

    private final JournalRepository journalRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void write(final Long userId, final String journalText) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        boolean alreadyWritten = journalRepository.existsByUserIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanAndDeletedAtIsNull(
                userId, start, end
        );

        if (alreadyWritten) {
            throw new DailyJournalLimitExceededException();
        }

        Journal journal = journalRepository.save(Journal.write(userId, journalText));

        eventPublisher.publishEvent(
                new JournalWrittenEvent(
                        journal.getUserId(),
                        journal.getId(),
                        journal.getCreatedAt()
                )
        );
    }

}
