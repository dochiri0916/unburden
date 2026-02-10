package com.unburden.application.auth.command;

import com.unburden.application.user.query.UserFinder;
import com.unburden.domain.auth.InvalidCredentialsException;
import com.unburden.domain.user.User;
import com.unburden.domain.user.UserNotActiveException;
import com.unburden.presentation.auth.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

    private final UserFinder userFinder;
    private final PasswordEncoder passwordEncoder;

    public User authenticate(final LoginRequest request) {
        User user = userFinder.findByEmail(request.email());

        if (user.isDeleted()) {
            throw new UserNotActiveException();
        }

        validatePassword(request.password(), user.getPassword());

        return user;
    }

    private void validatePassword(
            final String rawPassword,
            final String encodedPassword
    ) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidCredentialsException();
        }
    }

}