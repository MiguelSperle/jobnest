package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class Job {
    private final String id;
    private final String userId;
    private final String title;
    private final String description;
    private final SeniorityLevel seniorityLevel;
    private final Modality modality;
    private final LocalDateTime createdAt;

    private Job(
            String id,
            String userId,
            String title,
            String description,
            SeniorityLevel seniorityLevel,
            Modality modality,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.seniorityLevel = seniorityLevel;
        this.modality = modality;
        this.createdAt = createdAt;
    }

    public static Job newJob(
            String userId,
            String title,
            String description,
            SeniorityLevel seniorityLevel,
            Modality modality
    ) {
        return new Job(
                IdentifierUtils.generateUUID(),
                userId,
                title,
                description,
                seniorityLevel,
                modality,
                TimeUtils.now()
        );
    }

    public static Job with(
            String id,
            String userId,
            String title,
            String description,
            SeniorityLevel seniorityLevel,
            Modality modality,
            LocalDateTime createdAt
    ) {
        return new Job(
                id,
                userId,
                title,
                description,
                seniorityLevel,
                modality,
                createdAt
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

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + this.id + '\'' +
                ", userId='" + this.userId + '\'' +
                ", title='" + this.title + '\'' +
                ", description='" + this.description + '\'' +
                ", seniorityLevel=" + this.seniorityLevel +
                ", modality=" + this.modality +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
