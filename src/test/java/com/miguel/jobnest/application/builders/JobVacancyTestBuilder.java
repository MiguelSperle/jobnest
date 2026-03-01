package com.miguel.jobnest.application.builders;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.util.Objects;

public class JobVacancyTestBuilder {
    private String userId;

    private JobVacancyTestBuilder() {
    }

    public static JobVacancyTestBuilder aJobVacancy() {
        return new JobVacancyTestBuilder();
    }

    public JobVacancyTestBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public JobVacancy build() {
        Objects.requireNonNull(this.userId, "UserId must not be null");

        return JobVacancy.with(
                IdentifierUtils.generateNewId(),
                this.userId,
                "Java Developer",
                "This is the job vacancy description",
                SeniorityLevel.JUNIOR,
                Modality.REMOTE,
                "Company Name",
                false,
                TimeUtils.now()
        );
    }
}