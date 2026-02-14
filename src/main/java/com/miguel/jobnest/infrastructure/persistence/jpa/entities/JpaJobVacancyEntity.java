package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_vacancies")
@AllArgsConstructor
@NoArgsConstructor
public class JpaJobVacancyEntity {
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

    @Column(name = "company_name", nullable = false, length = 80)
    private String companyName;

    @Column(nullable = false, name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaJobVacancyEntity toEntity(JobVacancy jobVacancy) {
        return new JpaJobVacancyEntity(
                jobVacancy.getId(),
                jobVacancy.getUserId(),
                jobVacancy.getTitle(),
                jobVacancy.getDescription(),
                jobVacancy.getSeniorityLevel(),
                jobVacancy.getModality(),
                jobVacancy.getCompanyName(),
                jobVacancy.getIsDeleted(),
                jobVacancy.getCreatedAt()
        );
    }

    public JobVacancy toDomain() {
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
