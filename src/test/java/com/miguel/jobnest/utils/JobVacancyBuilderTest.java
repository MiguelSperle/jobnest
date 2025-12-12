package com.miguel.jobnest.utils;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

public class JobVacancyBuilderTest {
    public static JobVacancy build(String userId) {
        return JobVacancy.with(
                IdentifierUtils.generateUUID(),
                userId,
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
