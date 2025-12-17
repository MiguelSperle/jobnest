package com.miguel.jobnest.utils;

import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserCodeTestBuilder {
    private String userId;
    private UserCodeType userCodeType;
    private LocalDateTime expiresIn;

    private UserCodeTestBuilder() {
    }

    public static UserCodeTestBuilder aUserCode() {
        return new UserCodeTestBuilder();
    }

    public UserCodeTestBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserCodeTestBuilder userCodeType(UserCodeType userCodeType) {
        this.userCodeType = userCodeType;
        return this;
    }

    public UserCodeTestBuilder expiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public UserCode build() {
        Objects.requireNonNull(this.userId, "UserId must not be null");
        Objects.requireNonNull(this.userCodeType, "UserCodeType must not be null");
        Objects.requireNonNull(this.expiresIn, "ExpiresIn must not be null");

        return UserCode.with(
                IdentifierUtils.generateNewId(),
                this.userId,
                "1AB23CT2",
                this.userCodeType,
                this.expiresIn,
                TimeUtils.now()
        );
    }
}

