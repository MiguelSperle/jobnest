package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class JpaUserEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "curriculum_url", nullable = false)
    private String curriculumUrl;

    @Column(name = "authorization_role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private AuthorizationRole authorizationRole;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaUserEntity from(User user) {
        return new JpaUserEntity(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getCurriculumUrl(),
                user.getAuthorizationRole(),
                user.getCreatedAt()
        );
    }

    public User toEntity() {
        return User.with(
                this.id,
                this.fullName,
                this.email,
                this.phoneNumber,
                this.password,
                this.curriculumUrl,
                this.authorizationRole,
                this.createdAt
        );
    }
}
