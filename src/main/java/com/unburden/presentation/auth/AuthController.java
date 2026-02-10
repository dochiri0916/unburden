package com.unburden.presentation.auth;

import com.unburden.application.auth.dto.LoginResult;
import com.unburden.application.auth.facade.LoginFacade;
import com.unburden.application.auth.facade.ReissueTokenFacade;
import com.unburden.infrastructure.security.cookie.CookieProvider;
import com.unburden.presentation.auth.request.LoginRequest;
import com.unburden.presentation.auth.response.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginFacade loginFacade;
    private final CookieProvider cookieProvider;
    private final ReissueTokenFacade reissueTokenFacade;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        LoginResult loginResult = loginFacade.login(request);

        cookieProvider.addRefreshToken(response, loginResult.refreshToken());

        return ResponseEntity.ok(
                AuthResponse.from(loginResult.user(), loginResult.accessToken())
        );
    }

    @PostMapping("/reissue")
    public ResponseEntity<AuthResponse> reissue(
            @CookieValue(name = "refreshToken") String refreshToken
    ) {
        return ResponseEntity.ok(
                reissueTokenFacade.reissue(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        cookieProvider.deleteRefreshToken(response);
        return ResponseEntity.noContent().build();
    }

}