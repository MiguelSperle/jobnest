package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.Subscription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class JpaSubscriptionEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(name = "job_vacancy_id", nullable = false, length = 36)
    private String jobVacancyId;

    @Column(name = "resume_url", nullable = false)
    private String resumeUrl;

    @Column(nullable = false, name = "is_canceled")
    private Boolean isCanceled;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected JpaSubscriptionEntity() {
    }

    private JpaSubscriptionEntity(
            final String id,
            final String userId,
            final String jobVacancyId,
            final String resumeUrl,
            final Boolean isCanceled,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.jobVacancyId = jobVacancyId;
        this.resumeUrl = resumeUrl;
        this.isCanceled = isCanceled;
        this.createdAt = createdAt;
    }

    public static JpaSubscriptionEntity toEntity(final Subscription subscription) {
        return new JpaSubscriptionEntity(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getJobVacancyId(),
                subscription.getResumeUrl(),
                subscription.getIsCanceled(),
                subscription.getCreatedAt()
        );
    }

    public Subscription toDomain() {
        return Subscription.with(
                this.id,
                this.userId,
                this.jobVacancyId,
                this.resumeUrl,
                this.isCanceled,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getJobVacancyId() {
        return this.jobVacancyId;
    }

    public String getResumeUrl() {
        return this.resumeUrl;
    }

    public Boolean getIsCanceled() {
        return this.isCanceled;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
