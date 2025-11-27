package com.miguel.jobnest.infrastructure.persistence.jpa.specifications;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaJobEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaJobSpecification {
    public static Specification<JpaJobEntity> filterByTerms(String terms) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.or(
                        criterialBuilder.like(criterialBuilder.lower(root.get("title")), "%" + terms.toLowerCase() + "%"),
                        criterialBuilder.like(criterialBuilder.lower(root.get("description")), "%" + terms.toLowerCase() + "%")
                );
    }

    public static Specification<JpaJobEntity> filterByUserId(String userId) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("userId"), userId);
    }
}
