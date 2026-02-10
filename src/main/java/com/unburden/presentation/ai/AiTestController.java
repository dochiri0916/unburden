package com.unburden.presentation.ai;

import com.unburden.application.ai.AiAnalyzeService;
import com.unburden.presentation.ai.request.AiAnalyzeHttpRequest;
import com.unburden.presentation.ai.response.AiAnalyzeHttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiTestController {

    private final AiAnalyzeService aiAnalyzeService;

    @PostMapping
    public AiAnalyzeHttpResponse analyze(@RequestBody AiAnalyzeHttpRequest request) {
        AiAnalyzeService.Result result = aiAnalyzeService.analyze(request.text());

        return new AiAnalyzeHttpResponse(
                result.opening(),
                result.body(),
                result.closing()
        );
    }

}