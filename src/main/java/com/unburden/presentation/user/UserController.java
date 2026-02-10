package com.unburden.presentation.user;

import com.unburden.application.user.command.RegisterService;
import com.unburden.application.user.query.UserQueryService;
import com.unburden.infrastructure.security.jwt.JwtPrincipal;
import com.unburden.presentation.user.request.RegisterRequest;
import com.unburden.presentation.user.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final RegisterService registerService;
    private final UserQueryService userQueryService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(
                UserResponse.from(registerService.register(request))
        );
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(
            @AuthenticationPrincipal JwtPrincipal principal
    ) {
        return ResponseEntity.ok(
                UserResponse.from(
                        userQueryService.getActiveUser(principal.userId())
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getActiveUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                UserResponse.from(userQueryService.getActiveUser(id)
                )
        );
    }

}