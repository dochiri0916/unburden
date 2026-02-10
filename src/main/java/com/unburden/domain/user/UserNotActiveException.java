package com.unburden.domain.user;

public class UserNotActiveException extends UserException {
    public UserNotActiveException() {
        super("삭제된 사용자입니다.");
    }
}