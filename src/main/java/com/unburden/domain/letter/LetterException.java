package com.unburden.domain.letter;

public class LetterException extends RuntimeException {
    public LetterException(String message) {
        super(message);
    }
    public LetterException(String message, Throwable cause) {
        super(message, cause);
    }
}