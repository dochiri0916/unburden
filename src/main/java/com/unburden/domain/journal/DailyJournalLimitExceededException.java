package com.unburden.domain.journal;

public class DailyJournalLimitExceededException extends JournalException {
    public DailyJournalLimitExceededException() {
        super("하루에 하나의 일기만 작성할 수 있습니다.");
    }
}