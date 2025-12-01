package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class JobVacancy {
    private final String id;
    private final String userId;
    private final String title;
    private final String description;
    private final SeniorityLevel seniorityLevel;
    private final Modality modality;
    private final String companyName;
    private final Boolean isDeleted;
    private final LocalDateTime createdAt;

    private JobVacancy(
            String id,
            String userId,
            String title,
            String description,
            SeniorityLevel seniorityLevel,
            Modality modality,
            String companyName,
            Boolean isDeleted,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.seniorityLevel = seniorityLevel;
        this.modality = modality;
        this.companyName = companyName;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public static JobVacancy newJobVacancy(
            String userId,
            String title,
            String description,
            SeniorityLevel seniorityLevel,
            Modality modality,
            String companyName
    ) {
        return new JobVacancy(
                IdentifierUtils.generateUUID(),
                userId,
                title,
                description,
                seniorityLevel,
                modality,
                companyName,
                false,
                TimeUtils.now()
        );
    }

    public static JobVacancy with(
            String id,
            String userId,
            String title,
            String description,
            SeniorityLevel seniorityLevel,
            Modality modality,
            String companyName,
            Boolean isDeleted,
            LocalDateTime createdAt
    ) {
        return new JobVacancy(
                id,
                userId,
                title,
                description,
                seniorityLevel,
                modality,
                companyName,
                isDeleted,
                createdAt
        );
    }

    public JobVacancy withTitle(String title) {
        return new JobVacancy(
                this.id,
                this.userId,
                title,
                this.description,
                this.seniorityLevel,
                this.modality,
                this.companyName,
                this.isDeleted,
                this.createdAt
        );
    }

    public JobVacancy withDescription(String description) {
        return new JobVacancy(
                this.id,
                this.userId,
                this.title,
                description,
                this.seniorityLevel,
                this.modality,
                this.companyName,
                this.isDeleted,
                this.createdAt
        );
    }

    public JobVacancy withSeniorityLevel(SeniorityLevel seniorityLevel) {
        return new JobVacancy(
                this.id,
                this.userId,
                this.title,
                this.description,
                seniorityLevel,
                this.modality,
                this.companyName,
                this.isDeleted,
                this.createdAt
        );
    }

    public JobVacancy withModality(Modality modality) {
        return new JobVacancy(
                this.id,
                this.userId,
                this.title,
                this.description,
                this.seniorityLevel,
                modality,
                this.companyName,
                this.isDeleted,
                this.createdAt
        );
    }

    public JobVacancy withCompanyName(String companyName) {
        return new JobVacancy(
                this.id,
                this.userId,
                this.title,
                this.description,
                this.seniorityLevel,
                this.modality,
                companyName,
                this.isDeleted,
                this.createdAt
        );
    }

    public JobVacancy withIsDeleted(Boolean isDeleted) {
        return new JobVacancy(
                this.id,
                this.userId,
                this.title,
                this.description,
                this.seniorityLevel,
                this.modality,
                this.companyName,
                isDeleted,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public SeniorityLevel getSeniorityLevel() {
        return this.seniorityLevel;
    }

    public Modality getModality() {
        return this.modality;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "JobVacancy{" +
                "id='" + this.id + '\'' +
                ", userId='" + this.userId + '\'' +
                ", title='" + this.title + '\'' +
                ", description='" + this.description + '\'' +
                ", seniorityLevel=" + this.seniorityLevel +
                ", modality=" + this.modality +
                ", companyName='" + this.companyName + '\'' +
                ", isDeleted='" + this.isDeleted + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
