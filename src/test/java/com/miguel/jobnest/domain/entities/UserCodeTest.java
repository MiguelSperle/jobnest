package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserCodeTest {

    @Test
    void shouldReturnUserCode_whenCallNewUserCode() {
        final String userId = IdentifierUtils.generateUUID();
        final String code = "1A2C3V4A";

        final UserCode newUserCode = UserCode.newUserCode(
                userId,
                code,
                UserCodeType.USER_VERIFICATION
        );

        Assertions.assertNotNull(newUserCode);
        Assertions.assertNotNull(newUserCode.getId());
        Assertions.assertEquals(userId, newUserCode.getUserId());
        Assertions.assertEquals(code, newUserCode.getCode());
        Assertions.assertEquals(UserCodeType.USER_VERIFICATION, newUserCode.getUserCodeType());
        Assertions.assertNotNull(newUserCode.getExpiresIn());
        Assertions.assertNotNull(newUserCode.getCreatedAt());
    }

    @Test
    void shouldReturnUserCode_whenCallWith() {
        final String id = IdentifierUtils.generateUUID();
        final String userId = IdentifierUtils.generateUUID();
        final String code = "1A2C3V4A";
        final LocalDateTime expiresIn = TimeUtils.now().plusMinutes(15);
        final LocalDateTime createdAt = TimeUtils.now();

        final UserCode userCode = UserCode.with(
                id,
                userId,
                code,
                UserCodeType.USER_VERIFICATION,
                expiresIn,
                createdAt
        );

        Assertions.assertNotNull(userCode);
        Assertions.assertEquals(id, userCode.getId());
        Assertions.assertEquals(userId, userCode.getUserId());
        Assertions.assertEquals(code, userCode.getCode());
        Assertions.assertEquals(UserCodeType.USER_VERIFICATION, userCode.getUserCodeType());
        Assertions.assertEquals(expiresIn, userCode.getExpiresIn());
        Assertions.assertEquals(createdAt, userCode.getCreatedAt());
    }

    @Test
    void shouldReturnFormattedUserCode_whenCallToString() {
        final String id = IdentifierUtils.generateUUID();
        final String userId = IdentifierUtils.generateUUID();
        final String code = "1A2C3V4A";
        final LocalDateTime expiresIn = TimeUtils.now().plusMinutes(15);
        final LocalDateTime createdAt = TimeUtils.now();

        final UserCode userCode = UserCode.with(
                id,
                userId,
                code,
                UserCodeType.USER_VERIFICATION,
                expiresIn,
                createdAt
        );

        final String expectedToString = "UserCode{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", code='" + code + '\'' +
                ", userCodeType=" + UserCodeType.USER_VERIFICATION +
                ", expiresIn=" + expiresIn +
                ", createdAt=" + createdAt +
                '}';

        Assertions.assertEquals(expectedToString, userCode.toString());
    }
}
