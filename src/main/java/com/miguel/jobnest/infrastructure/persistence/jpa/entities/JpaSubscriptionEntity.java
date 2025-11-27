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

    @Column(name = "job_id", nullable = false, length = 36)
    private String jobId;

    @Column(name = "resume_url", nullable = false)
    private String resumeUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaSubscriptionEntity from(Subscription subscription) {
        return new JpaSubscriptionEntity(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getJobId(),
                subscription.getResumeUrl(),
                subscription.getCreatedAt()
        );
    }

    public Subscription toEntity() {
        return Subscription.with(
                this.id,
                this.userId,
                this.jobId,
                this.resumeUrl,
                this.createdAt
        );
    }
}
