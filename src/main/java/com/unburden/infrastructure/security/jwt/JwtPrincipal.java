package com.unburden.infrastructure.security.jwt;

public record JwtPrincipal(
        Long userId,
        String role
) {
}