package com.unburden.domain.auth;

public class RefreshTokenOwnerMismatchException extends RefreshTokenException {
    public RefreshTokenOwnerMismatchException() {
        super("리프레시 토큰의 소유자가 일치하지 않습니다.");
    }
}