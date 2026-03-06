package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_codes")
public class JpaUserCodeEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(nullable = false, unique = true, length = 8)
    private String code;

    @Column(name = "code_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UserCodeType userCodeType;

    @Column(name = "expires_in", nullable = false)
    private LocalDateTime expiresIn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected JpaUserCodeEntity() {
    }

    private JpaUserCodeEntity(
            final String id,
            final String userId,
            final String code,
            final UserCodeType userCodeType,
            final LocalDateTime expiresIn,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.userCodeType = userCodeType;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    public static JpaUserCodeEntity toEntity(final UserCode userCode) {
        return new JpaUserCodeEntity(
                userCode.getId(),
                userCode.getUserId(),
                userCode.getCode(),
                userCode.getUserCodeType(),
                userCode.getExpiresIn(),
                userCode.getCreatedAt()
        );
    }

    public UserCode toDomain() {
        return UserCode.with(
                this.id,
                this.userId,
                this.code,
                this.userCodeType,
                this.expiresIn,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getCode() {
        return this.code;
    }

    public UserCodeType getUserCodeType() {
        return this.userCodeType;
    }

    public LocalDateTime getExpiresIn() {
        return this.expiresIn;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
