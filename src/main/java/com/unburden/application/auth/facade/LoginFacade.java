package com.unburden.application.auth.facade;

import com.unburden.application.auth.command.RefreshTokenIssueService;
import com.unburden.application.auth.command.UserAuthenticationService;
import com.unburden.application.auth.dto.LoginResult;
import com.unburden.domain.user.User;
import com.unburden.infrastructure.security.jwt.JwtTokenGenerator;
import com.unburden.infrastructure.security.jwt.JwtTokenResult;
import com.unburden.presentation.auth.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LoginFacade {

    private final UserAuthenticationService userAuthenticationService;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenIssueService refreshTokenIssueService;

    @Transactional
    public LoginResult login(final LoginRequest request) {
        User user = userAuthenticationService.authenticate(request);

        user.updateLastLoginAt();

        JwtTokenResult tokenResult = jwtTokenGenerator.generateToken(user.getId(), user.getRole().name());

        refreshTokenIssueService.issue(
                tokenResult.refreshToken(),
                user.getId(),
                tokenResult.refreshTokenExpiresAt()
        );

        return new LoginResult(user, tokenResult.accessToken(), tokenResult.refreshToken());
    }

}