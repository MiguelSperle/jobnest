package com.miguel.jobnest.testsupport.builders.entities.jpa;

import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaUserEntity;

public class JpaUserEntityTestBuilder {
    private JpaUserEntityTestBuilder() {
    }

    public static JpaUserEntityTestBuilder aJpaUserEntity() {
        return new JpaUserEntityTestBuilder();
    }

    public JpaUserEntity build() {
        return JpaUserEntity.with(
                IdentifierUtils.generateNewId(),
                "Jason",
                "jason123@gmail.com",
                "This is my description about me",
                "12345",
                UserStatus.UNVERIFIED,
                AuthorizationRole.RECRUITER,
                "New York",
                "New York",
                "USA",
                TimeUtils.now()
        );
    }
}
