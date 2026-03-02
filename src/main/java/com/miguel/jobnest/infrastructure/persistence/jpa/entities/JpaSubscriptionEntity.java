package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.Subscription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@AllArgsConstructor
@NoArgsConstructor
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
}
