package com.unburden.application.auth.dto;

import com.unburden.domain.user.User;

public record LoginResult(
        User user,
        String accessToken,
        String refreshToken
) {
}