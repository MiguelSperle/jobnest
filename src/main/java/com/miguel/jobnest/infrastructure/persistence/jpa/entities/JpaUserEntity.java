package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class JpaUserEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 155)
    private String email;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "authorization_role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private AuthorizationRole authorizationRole;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String state;

    @Column(nullable = false, length = 50)
    private String country;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected JpaUserEntity() {
    }

    private JpaUserEntity(
            final String id,
            final String name,
            final String email,
            final String description,
            final String password,
            final UserStatus userStatus,
            final AuthorizationRole authorizationRole,
            final String city,
            final String state,
            final String country,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.description = description;
        this.password = password;
        this.userStatus = userStatus;
        this.authorizationRole = authorizationRole;
        this.city = city;
        this.state = state;
        this.country = country;
        this.createdAt = createdAt;
    }

    public static JpaUserEntity toEntity(final User user) {
        return new JpaUserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getDescription(),
                user.getPassword(),
                user.getUserStatus(),
                user.getAuthorizationRole(),
                user.getCity(),
                user.getState(),
                user.getCountry(),
                user.getCreatedAt()
        );
    }

    public User toDomain() {
        return User.with(
                this.id,
                this.name,
                this.email,
                this.description,
                this.password,
                this.userStatus,
                this.authorizationRole,
                this.city,
                this.state,
                this.country,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPassword() {
        return this.password;
    }

    public UserStatus getUserStatus() {
        return this.userStatus;
    }

    public AuthorizationRole getAuthorizationRole() {
        return this.authorizationRole;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
