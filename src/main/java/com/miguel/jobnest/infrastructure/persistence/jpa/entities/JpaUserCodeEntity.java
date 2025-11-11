package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_codes")
@AllArgsConstructor
@NoArgsConstructor
public class JpaUserCodeEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(nullable = false, unique = true, length = 6)
    private String code;

    @Column(name = "code_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UserCodeType userCodeType;

    @Column(name = "expires_in", nullable = false)
    private LocalDateTime expiresIn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaUserCodeEntity from(UserCode userCode) {
        return new JpaUserCodeEntity(
                userCode.getId(),
                userCode.getUserId(),
                userCode.getCode(),
                userCode.getUserCodeType(),
                userCode.getExpiresIn(),
                userCode.getCreatedAt()
        );
    }

    public UserCode toEntity() {
        return UserCode.with(
                this.id,
                this.userId,
                this.code,
                this.userCodeType,
                this.expiresIn,
                this.createdAt
        );
    }
}
