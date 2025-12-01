package com.miguel.jobnest.infrastructure.persistence.jpa.specifications;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaSubscriptionEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaSubscriptionSpecification {
    public static Specification<JpaSubscriptionEntity> filterByUserId(String userId) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("userId"), userId);
    }
}
