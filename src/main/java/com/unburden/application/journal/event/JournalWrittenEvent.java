package com.unburden.application.journal.event;

import java.time.LocalDateTime;

public record JournalWrittenEvent(
        Long userId,
        Long journalId,
        LocalDateTime writtenAt
) {
}