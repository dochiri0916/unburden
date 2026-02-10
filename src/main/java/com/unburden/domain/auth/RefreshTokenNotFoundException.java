package com.unburden.domain.auth;

public class RefreshTokenNotFoundException extends RefreshTokenException {
    public RefreshTokenNotFoundException() {
        super("유효하지 않은 토큰입니다.");
    }
}