package com.unburden.presentation.journal;

import com.unburden.application.journal.command.WriteJournalService;
import com.unburden.infrastructure.security.jwt.JwtPrincipal;
import com.unburden.presentation.journal.request.WriteJournalRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/journals")
@RequiredArgsConstructor
public class JournalController {

    private final WriteJournalService writeJournalService;

    @PostMapping
    public ResponseEntity<Void> write(
            @AuthenticationPrincipal JwtPrincipal principal,
            @Valid @RequestBody WriteJournalRequest request
    ) {
        writeJournalService.write(principal.userId(), request.content());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
