package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.testsupport.builders.entities.domain.UserCodeTestBuilder;
import com.miguel.jobnest.testsupport.builders.entities.jpa.JpaUserCodeEntityTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class JpaUserCodeEntityTest {
    @Test
    void shouldReturnJpaUserCodeEntity_whenCallToEntity() {
        final UserCode userCode = UserCodeTestBuilder.aUserCode().userId(IdentifierUtils.generateNewId()).userCodeType(UserCodeType.USER_VERIFICATION).expiresIn(TimeUtils.now().plusMinutes(15)).build();

        final JpaUserCodeEntity transformedIntoJpaEntity = JpaUserCodeEntity.toEntity(userCode);

        Assertions.assertNotNull(transformedIntoJpaEntity);
        Assertions.assertEquals(userCode.getId(), transformedIntoJpaEntity.getId());
        Assertions.assertEquals(userCode.getUserId(), transformedIntoJpaEntity.getUserId());
        Assertions.assertEquals(userCode.getCode(), transformedIntoJpaEntity.getCode());
        Assertions.assertEquals(userCode.getUserCodeType(), transformedIntoJpaEntity.getUserCodeType());
        Assertions.assertEquals(userCode.getExpiresIn(), transformedIntoJpaEntity.getExpiresIn());
        Assertions.assertEquals(userCode.getCreatedAt(), transformedIntoJpaEntity.getCreatedAt());
    }

    @Test
    void shouldReturnUserCode_whenCallToDomain() {
        final JpaUserCodeEntity jpaUserCodeEntity = JpaUserCodeEntityTestBuilder.aJpaUserCodeEntity().build();

        final UserCode transformedIntoDomainEntity = jpaUserCodeEntity.toDomain();

        Assertions.assertNotNull(transformedIntoDomainEntity);
        Assertions.assertEquals(jpaUserCodeEntity.getId(), transformedIntoDomainEntity.getId());
        Assertions.assertEquals(jpaUserCodeEntity.getUserId(), transformedIntoDomainEntity.getUserId());
        Assertions.assertEquals(jpaUserCodeEntity.getCode(), transformedIntoDomainEntity.getCode());
        Assertions.assertEquals(jpaUserCodeEntity.getUserCodeType(), transformedIntoDomainEntity.getUserCodeType());
        Assertions.assertEquals(jpaUserCodeEntity.getExpiresIn(), transformedIntoDomainEntity.getExpiresIn());
        Assertions.assertEquals(jpaUserCodeEntity.getCreatedAt(), transformedIntoDomainEntity.getCreatedAt());
    }

    @Test
    void shouldReturnJpaUserCodeEntity_whenCallWith() {
        final String id = IdentifierUtils.generateNewId();
        final String userId = IdentifierUtils.generateNewId();
        final String code = "A1BC32";
        final UserCodeType userCodeType = UserCodeType.USER_VERIFICATION;
        final LocalDateTime expiresIn = TimeUtils.now().plusMinutes(15);
        final LocalDateTime createdAt = TimeUtils.now();

        final JpaUserCodeEntity jpaUserCodeEntity = JpaUserCodeEntity.with(
                id,
                userId,
                code,
                userCodeType,
                expiresIn,
                createdAt
        );

        Assertions.assertNotNull(jpaUserCodeEntity);
        Assertions.assertEquals(id, jpaUserCodeEntity.getId());
        Assertions.assertEquals(userId, jpaUserCodeEntity.getUserId());
        Assertions.assertEquals(code, jpaUserCodeEntity.getCode());
        Assertions.assertEquals(userCodeType, jpaUserCodeEntity.getUserCodeType());
        Assertions.assertEquals(expiresIn, jpaUserCodeEntity.getExpiresIn());
        Assertions.assertEquals(createdAt, jpaUserCodeEntity.getCreatedAt());
    }
}
