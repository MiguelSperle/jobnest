package com.miguel.jobnest.domain.builders;

import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;

import java.time.LocalDateTime;

public class UserCodeBuilder {
    private String id;
    private String userId;
    private String code;
    private UserCodeType userCodeType;
    private LocalDateTime expiresIn;
    private LocalDateTime createdAt;

    public static UserCodeBuilder userCode() {
        return new UserCodeBuilder();
    }

    public UserCodeBuilder id(String id) {
        this.id = id;
        return this;
    }

    public UserCodeBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserCodeBuilder code(String code) {
        this.code = code;
        return this;
    }

    public UserCodeBuilder userCodeType(UserCodeType userCodeType) {
        this.userCodeType = userCodeType;
        return this;
    }

    public UserCodeBuilder expiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public UserCodeBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserCode build() {
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

