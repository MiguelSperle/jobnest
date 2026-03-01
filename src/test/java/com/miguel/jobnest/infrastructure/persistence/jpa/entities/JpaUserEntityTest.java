package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.testsupport.builders.entities.domain.UserTestBuilder;
import com.miguel.jobnest.testsupport.builders.entities.jpa.JpaUserEntityTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class JpaUserEntityTest {
    @Test
    void shouldReturnJpaUserEntity_whenCallToEntity() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final JpaUserEntity transformedIntoJpaEntity = JpaUserEntity.toEntity(user);

        Assertions.assertNotNull(transformedIntoJpaEntity);
        Assertions.assertEquals(user.getId(), transformedIntoJpaEntity.getId());
        Assertions.assertEquals(user.getName(), transformedIntoJpaEntity.getName());
        Assertions.assertEquals(user.getEmail(), transformedIntoJpaEntity.getEmail());
        Assertions.assertEquals(user.getDescription(), transformedIntoJpaEntity.getDescription());
        Assertions.assertEquals(user.getPassword(), transformedIntoJpaEntity.getPassword());
        Assertions.assertEquals(user.getUserStatus(), transformedIntoJpaEntity.getUserStatus());
        Assertions.assertEquals(user.getAuthorizationRole(), transformedIntoJpaEntity.getAuthorizationRole());
        Assertions.assertEquals(user.getCity(), transformedIntoJpaEntity.getCity());
        Assertions.assertEquals(user.getState(), transformedIntoJpaEntity.getState());
        Assertions.assertEquals(user.getCountry(), transformedIntoJpaEntity.getCountry());
        Assertions.assertEquals(user.getCreatedAt(), transformedIntoJpaEntity.getCreatedAt());
    }

    @Test
    void shouldReturnUser_whenCallToDomain() {
        final JpaUserEntity jpaUserEntity = JpaUserEntityTestBuilder.aJpaUserEntity().build();

        final User transformedIntoDomainEntity = jpaUserEntity.toDomain();

        Assertions.assertNotNull(transformedIntoDomainEntity);
        Assertions.assertEquals(jpaUserEntity.getId(), transformedIntoDomainEntity.getId());
        Assertions.assertEquals(jpaUserEntity.getName(), transformedIntoDomainEntity.getName());
        Assertions.assertEquals(jpaUserEntity.getEmail(), transformedIntoDomainEntity.getEmail());
        Assertions.assertEquals(jpaUserEntity.getDescription(), transformedIntoDomainEntity.getDescription());
        Assertions.assertEquals(jpaUserEntity.getPassword(), transformedIntoDomainEntity.getPassword());
        Assertions.assertEquals(jpaUserEntity.getUserStatus(), transformedIntoDomainEntity.getUserStatus());
        Assertions.assertEquals(jpaUserEntity.getAuthorizationRole(), transformedIntoDomainEntity.getAuthorizationRole());
        Assertions.assertEquals(jpaUserEntity.getCity(), transformedIntoDomainEntity.getCity());
        Assertions.assertEquals(jpaUserEntity.getState(), transformedIntoDomainEntity.getState());
        Assertions.assertEquals(jpaUserEntity.getCountry(), transformedIntoDomainEntity.getCountry());
        Assertions.assertEquals(jpaUserEntity.getCreatedAt(), transformedIntoDomainEntity.getCreatedAt());
    }

    @Test
    void shouldReturnJpaUserEntity_whenCallWith() {
        final String id = IdentifierUtils.generateNewId();
        final String name = "Jason";
        final String email = "jason123@gmail.com";
        final String description = "This is my description about me";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.RECRUITER;
        final String city = "New York";
        final String state = "New York";
        final String country = "USA";
        final LocalDateTime createdAt = TimeUtils.now();

        final JpaUserEntity jpaUserEntity = JpaUserEntity.with(
                id,
                name,
                email,
                description,
                password,
                userStatus,
                authorizationRole,
                city,
                state,
                country,
                createdAt
        );

        Assertions.assertEquals(id, jpaUserEntity.getId());
        Assertions.assertEquals(name, jpaUserEntity.getName());
        Assertions.assertEquals(email, jpaUserEntity.getEmail());
        Assertions.assertEquals(description, jpaUserEntity.getDescription());
        Assertions.assertEquals(password, jpaUserEntity.getPassword());
        Assertions.assertEquals(userStatus, jpaUserEntity.getUserStatus());
        Assertions.assertEquals(authorizationRole, jpaUserEntity.getAuthorizationRole());
        Assertions.assertEquals(city, jpaUserEntity.getCity());
        Assertions.assertEquals(state, jpaUserEntity.getState());
        Assertions.assertEquals(country, jpaUserEntity.getCountry());
        Assertions.assertEquals(createdAt, jpaUserEntity.getCreatedAt());
    }
}
