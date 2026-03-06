package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.builders.UserCodeBuilder;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserCodeTest {

    @Test
    void shouldReturnUserCode_whenCallNewUserCode() {
        final String userId = IdentifierUtils.generateNewId();
        final String code = "1A2C3V4A";
        final UserCodeType userCodeType = UserCodeType.USER_VERIFICATION;

        final UserCode newUserCode = UserCode.newUserCode(
                userId,
                code,
                userCodeType
        );

        Assertions.assertNotNull(newUserCode);
        Assertions.assertNotNull(newUserCode.getId());
        Assertions.assertEquals(userId, newUserCode.getUserId());
        Assertions.assertEquals(code, newUserCode.getCode());
        Assertions.assertEquals(userCodeType, newUserCode.getUserCodeType());
        Assertions.assertNotNull(newUserCode.getExpiresIn());
        Assertions.assertNotNull(newUserCode.getCreatedAt());
    }

    @Test
    void shouldReturnUserCode_whenCallWith() {
        final String id = IdentifierUtils.generateNewId();
        final String userId = IdentifierUtils.generateNewId();
        final String code = "1A2C3V4A";
        final UserCodeType userCodeType = UserCodeType.USER_VERIFICATION;
        final LocalDateTime expiresIn = TimeUtils.now().plusMinutes(15);
        final LocalDateTime createdAt = TimeUtils.now();

        final UserCode userCode = UserCode.with(
                id,
                userId,
                code,
                userCodeType,
                expiresIn,
                createdAt
        );

        Assertions.assertNotNull(userCode);
        Assertions.assertEquals(id, userCode.getId());
        Assertions.assertEquals(userId, userCode.getUserId());
        Assertions.assertEquals(code, userCode.getCode());
        Assertions.assertEquals(userCodeType, userCode.getUserCodeType());
        Assertions.assertEquals(expiresIn, userCode.getExpiresIn());
        Assertions.assertEquals(createdAt, userCode.getCreatedAt());
    }

    @Test
    void shouldReturnFormattedUserCode_whenCallToString() {
        final UserCode userCode = UserCodeBuilder.userCode()
                .id(IdentifierUtils.generateNewId())
                .code("123TYE")
                .userId(IdentifierUtils.generateNewId())
                .userCodeType(UserCodeType.USER_VERIFICATION)
                .expiresIn(TimeUtils.now().plusMinutes(15))
                .createdAt(TimeUtils.now())
                .build();

        final String expectedToString = "UserCode{" +
                "id='" + userCode.getId() + '\'' +
                ", userId='" + userCode.getUserId() + '\'' +
                ", code='" + userCode.getCode() + '\'' +
                ", userCodeType=" + userCode.getUserCodeType() +
                ", expiresIn=" + userCode.getExpiresIn() +
                ", createdAt=" + userCode.getCreatedAt() +
                '}';

        final String toStringResult = userCode.toString();

        Assertions.assertNotNull(toStringResult);
        Assertions.assertEquals(expectedToString, toStringResult);
    }
}
