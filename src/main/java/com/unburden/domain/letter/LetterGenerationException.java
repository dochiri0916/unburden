package com.unburden.domain.letter;

public class LetterGenerationException extends LetterException {
    public LetterGenerationException() {
        super("편지를 생성하지 못했습니다.");
    }

    public LetterGenerationException(Throwable cause) {
        super("편지를 생성하지 못했습니다.", cause);
    }
}