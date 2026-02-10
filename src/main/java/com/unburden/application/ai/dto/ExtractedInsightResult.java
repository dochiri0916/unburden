package com.unburden.application.ai.dto;

import java.util.List;

public record ExtractedInsightResult(
        String summary,
        List<String> tags,
        List<String> concerns
) {
}