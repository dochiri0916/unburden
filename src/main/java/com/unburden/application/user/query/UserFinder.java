package com.unburden.application.user.query;

import com.unburden.domain.user.User;

public interface UserFinder {

    User findActiveUserById(Long id);

    User findActiveUserByEmail(String email);

    User findByEmail(String email);

}