package com.unburden.domain.journal;

public class JournalNotFoundException extends RuntimeException {
    public JournalNotFoundException(Object value) {
        super("해당 일기를 찾을 수 없습니다: " + value);
    }
}