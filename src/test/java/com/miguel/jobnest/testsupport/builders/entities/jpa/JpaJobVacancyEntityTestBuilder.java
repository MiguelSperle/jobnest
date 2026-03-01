package com.miguel.jobnest.testsupport.builders.entities.jpa;

import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaJobVacancyEntity;

public class JpaJobVacancyEntityTestBuilder {

    private JpaJobVacancyEntityTestBuilder() {
    }

    public static JpaJobVacancyEntityTestBuilder aJpaJobVacancyEntity() {
        return new JpaJobVacancyEntityTestBuilder();
    }

    public JpaJobVacancyEntity build() {
        return JpaJobVacancyEntity.with(
                IdentifierUtils.generateNewId(),
                IdentifierUtils.generateNewId(),
                "Some title",
                "Some description",
                SeniorityLevel.JUNIOR,
                Modality.REMOTE,
                "Company name",
                false,
                TimeUtils.now()
        );
    }
}
