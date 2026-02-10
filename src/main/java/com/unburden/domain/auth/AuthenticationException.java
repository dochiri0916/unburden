package com.unburden.domain.auth;

public abstract class AuthenticationException extends RuntimeException {
    protected AuthenticationException(String message) {
        super(message);
    }
}