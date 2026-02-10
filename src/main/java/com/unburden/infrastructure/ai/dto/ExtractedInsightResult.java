package com.unburden.infrastructure.ai.dto;

import java.util.List;

public record ExtractedInsightResult(
        String aiSummary,
        List<String> tage,
        List<String> concerns
) {
}