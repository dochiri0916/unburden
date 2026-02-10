package com.unburden.application.auth.command;

import com.unburden.domain.auth.RefreshToken;
import com.unburden.infrastructure.persistence.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenIssueService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void issue(
            final String token,
            final Long userId,
            final LocalDateTime expiresAt
    ) {
        refreshTokenRepository.findByUserId(userId)
                .ifPresentOrElse(
                        existingToken -> existingToken.update(token, expiresAt),
                        () -> refreshTokenRepository.save(
                                RefreshToken.issue(token, userId, expiresAt)
                        )
                );
    }

}