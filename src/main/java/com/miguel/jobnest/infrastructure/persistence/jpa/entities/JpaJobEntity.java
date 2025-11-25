package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@AllArgsConstructor
@NoArgsConstructor
public class JpaJobEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(name = "seniority_level", nullable = false, length = 12)
    @Enumerated(EnumType.STRING)
    private SeniorityLevel seniorityLevel;

    @Column(nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    private Modality modality;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaJobEntity from(Job job) {
        return new JpaJobEntity(
                job.getId(),
                job.getUserId(),
                job.getTitle(),
                job.getDescription(),
                job.getSeniorityLevel(),
                job.getModality(),
                job.getCreatedAt()
        );
    }

    public Job toEntity() {
        return Job.with(
                this.id,
                this.userId,
                this.title,
                this.description,
                this.seniorityLevel,
                this.modality,
                this.createdAt
        );
    }
}
