package com.unburden.presentation.journal.request;

import jakarta.validation.constraints.NotBlank;

public record WriteJournalRequest(
        @NotBlank
        String content
) {
}
