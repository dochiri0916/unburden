package com.unburden.presentation.common.exception.mapper;

import com.unburden.domain.auth.InvalidRefreshTokenException;
import com.unburden.domain.auth.RefreshTokenException;
import com.unburden.domain.auth.RefreshTokenNotFoundException;
import com.unburden.domain.auth.RefreshTokenOwnerMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RefreshTokenExceptionStatusMapper implements DomainExceptionStatusMapper {

    private static final Map<Class<? extends RefreshTokenException>, HttpStatus> STATUS_MAP =
            Map.of(
                    RefreshTokenNotFoundException.class, HttpStatus.UNAUTHORIZED,
                    InvalidRefreshTokenException.class, HttpStatus.UNAUTHORIZED,
                    RefreshTokenOwnerMismatchException.class, HttpStatus.UNAUTHORIZED
            );

    @Override
    public boolean supports(RuntimeException exception) {
        return exception instanceof RefreshTokenException;
    }

    @Override
    public HttpStatus map(RuntimeException exception) {
        return STATUS_MAP.getOrDefault(((RefreshTokenException) exception).getClass(), HttpStatus.BAD_REQUEST);
    }
}