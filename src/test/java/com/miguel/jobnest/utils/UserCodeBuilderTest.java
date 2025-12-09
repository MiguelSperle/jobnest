package com.miguel.jobnest.utils;

import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class UserCodeBuilderTest {
    public static UserCode build(UserCodeType userCodeType, LocalDateTime expiresIn, String userId) {
        return UserCode.with(
                IdentifierUtils.generateUUID(),
                userId,
                "1AB23CT2",
                userCodeType,
                expiresIn,
                TimeUtils.now()
        );
    }
}
