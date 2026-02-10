package com.unburden.application.auth.facade;

import com.unburden.application.auth.query.RefreshTokenQueryService;
import com.unburden.application.user.query.UserFinder;
import com.unburden.domain.auth.InvalidRefreshTokenException;
import com.unburden.domain.auth.RefreshToken;
import com.unburden.domain.auth.RefreshTokenOwnerMismatchException;
import com.unburden.domain.user.User;
import com.unburden.infrastructure.security.jwt.JwtTokenGenerator;
import com.unburden.infrastructure.security.jwt.RefreshTokenVerifier;
import com.unburden.presentation.auth.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReissueTokenFacade {

    private final RefreshTokenVerifier refreshTokenVerifier;
    private final RefreshTokenQueryService refreshTokenQueryService;
    private final UserFinder userFinder;
    private final JwtTokenGenerator jwtTokenGenerator;

    public AuthResponse reissue(final String refreshTokenValue) {
        Long userId = refreshTokenVerifier.verifyAndExtractUserId(refreshTokenValue);

        RefreshToken refreshToken = refreshTokenQueryService.getValidToken(
                refreshTokenValue,
                LocalDateTime.now()
        );

        if (!refreshToken.isOwnedBy(userId)) {
            throw new RefreshTokenOwnerMismatchException();
        }

        User user = userFinder.findActiveUserById(userId);

        String newAccessToken = jwtTokenGenerator.generateAccessToken(
                user.getId(),
                user.getRole().name()
        );

        return AuthResponse.from(
                user,
                newAccessToken
        );
    }

}