package com.miguel.jobnest.testsupport.builders.entities.jpa;

import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaUserCodeEntity;

public class JpaUserCodeEntityTestBuilder {
    private JpaUserCodeEntityTestBuilder() {
    }

    public static JpaUserCodeEntityTestBuilder aJpaUserCodeEntity() {
        return new JpaUserCodeEntityTestBuilder();
    }

    public JpaUserCodeEntity build() {
        return JpaUserCodeEntity.with(
                IdentifierUtils.generateNewId(),
                IdentifierUtils.generateNewId(),
                "123ABC",
                UserCodeType.USER_VERIFICATION,
                TimeUtils.now().plusMinutes(15),
                TimeUtils.now()
        );
    }
}
