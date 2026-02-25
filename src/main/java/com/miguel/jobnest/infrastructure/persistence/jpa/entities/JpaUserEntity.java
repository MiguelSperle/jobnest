package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
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
}
