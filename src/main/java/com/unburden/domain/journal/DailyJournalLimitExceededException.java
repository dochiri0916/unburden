package com.unburden.domain.journal;

public class DailyJournalLimitExceededException extends JournalException {
    public DailyJournalLimitExceededException(String message) {
        super(message);
    }
}