package com.miguel.jobnest.testsupport.builders.entities.jpa;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaSubscriptionEntity;

public class JpaSubscriptionEntityTestBuilder {

    private JpaSubscriptionEntityTestBuilder() {
    }

    public static JpaSubscriptionEntityTestBuilder aJpaSubscriptionEntity() {
        return new JpaSubscriptionEntityTestBuilder();
    }

    public JpaSubscriptionEntity build() {
        return JpaSubscriptionEntity.with(
                IdentifierUtils.generateNewId(),
                IdentifierUtils.generateNewId(),
                IdentifierUtils.generateNewId(),
                "resume-url",
                false,
                TimeUtils.now()
        );
    }
}
