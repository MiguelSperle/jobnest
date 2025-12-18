package com.miguel.jobnest.application.utils;

import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.util.Objects;

public class UserTestBuilder {
    private UserStatus userStatus;
    private AuthorizationRole authorizationRole;

    private UserTestBuilder() {
    }

    public static UserTestBuilder aUser() {
        return new UserTestBuilder();
    }

    public UserTestBuilder userStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public UserTestBuilder authorizationRole(AuthorizationRole authorizationRole) {
        this.authorizationRole = authorizationRole;
        return this;
    }

    public User build() {
        Objects.requireNonNull(this.userStatus, "UserStatus must not be null");
        Objects.requireNonNull(this.authorizationRole, "AuthorizationRole must not be null");

        return User.with(
                IdentifierUtils.generateNewId(),
                "john",
                "johndoe1947@gmail.com",
                "This is my description about me",
                "12345@JH",
                this.userStatus,
                this.authorizationRole,
                "Louisville",
                "Kentucky",
                "United States",
                TimeUtils.now()
        );
    }
}
