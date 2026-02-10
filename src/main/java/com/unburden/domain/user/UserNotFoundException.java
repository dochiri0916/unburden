package com.unburden.domain.user;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(Object value) {
        super("해당 유저를 찾을 수 없습니다: " + value);
    }
}