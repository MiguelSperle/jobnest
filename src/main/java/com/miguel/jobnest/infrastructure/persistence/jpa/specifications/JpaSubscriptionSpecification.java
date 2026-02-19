package com.miguel.jobnest.infrastructure.persistence.jpa.specifications;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaSubscriptionEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaSubscriptionSpecification {
    public static Specification<JpaSubscriptionEntity> filterByUserId(final String userId) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<JpaSubscriptionEntity> filterByJobVacancyId(final String jobVacancyId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("jobVacancyId"), jobVacancyId));
    }

    public static Specification<JpaSubscriptionEntity> filterByIsCanceled(final boolean isCanceled) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("isCanceled"), isCanceled);
    }
}
