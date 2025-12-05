package com.miguel.jobnest.utils;

import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

public class UserBuilderTest {
    public static User build(UserStatus userStatus, AuthorizationRole authorizationRole) {
        return User.with(
                IdentifierUtils.generateUUID(),
                "john",
                "johnDoe1947@gmail.com",
                "This is my description about me",
                "12345@JH",
                userStatus,
                authorizationRole,
                "Louisville",
                "Kentucky",
                "United States",
                TimeUtils.now()
        );
    }
}
