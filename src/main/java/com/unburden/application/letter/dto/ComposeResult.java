package com.unburden.application.letter.dto;

import java.util.List;

public record ComposeResult(
        String opening,
        String body,
        String closing,
        List<String> keywords
) {
}