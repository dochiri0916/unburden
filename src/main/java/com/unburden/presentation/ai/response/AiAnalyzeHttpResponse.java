package com.unburden.presentation.ai.response;

public record AiAnalyzeHttpResponse(
        String opening,
        String body,
        String closing
) {
    public static AiAnalyzeHttpResponse from(
            String opening,
            String body,
            String closing
    ) {
        return new AiAnalyzeHttpResponse(opening, body, closing);
    }
}