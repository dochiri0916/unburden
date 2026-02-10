package com.unburden.domain.auth;

public class InvalidRefreshTokenException extends RefreshTokenException {
    public InvalidRefreshTokenException() {
        super("리프레시 토큰이 유효하지 않습니다.");
    }
}