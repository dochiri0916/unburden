package com.unburden.domain.user;

import com.unburden.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static java.util.Objects.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime lastLoginAt;

    public static User register(final String email, final String password, final String name) {
        User user = new User();
        user.email = requireNonNull(email);
        user.password = requireNonNull(password);
        user.name = requireNonNull(name);
        user.role = UserRole.USER;
        user.lastLoginAt = null;
        return user;
    }

    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

}