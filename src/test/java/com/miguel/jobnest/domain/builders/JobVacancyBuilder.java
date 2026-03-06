package com.miguel.jobnest.domain.builders;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;

import java.time.LocalDateTime;

public class JobVacancyBuilder {
    private String id;
    private String userId;
    private String title;
    private String description;
    private SeniorityLevel seniorityLevel;
    private Modality modality;
    private String companyName;
    private Boolean isDeleted;
    private LocalDateTime createdAt;

    public static JobVacancyBuilder jobVacancy() {
        return new JobVacancyBuilder();
    }

    public JobVacancyBuilder id(String id) {
        this.id = id;
        return this;
    }

    public JobVacancyBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public JobVacancyBuilder title(String title) {
        this.title = title;
        return this;
    }

    public JobVacancyBuilder description(String description) {
        this.description = description;
        return this;
    }

    public JobVacancyBuilder seniorityLevel(SeniorityLevel seniorityLevel) {
        this.seniorityLevel = seniorityLevel;
        return this;
    }

    public JobVacancyBuilder modality(Modality modality) {
        this.modality = modality;
        return this;
    }

    public JobVacancyBuilder companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public JobVacancyBuilder isDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public JobVacancyBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public JobVacancy build() {
        return JobVacancy.with(
                this.id,
                this.userId,
                this.title,
                this.description,
                this.seniorityLevel,
                this.modality,
                this.companyName,
                this.isDeleted,
                this.createdAt
        );
    }
}
