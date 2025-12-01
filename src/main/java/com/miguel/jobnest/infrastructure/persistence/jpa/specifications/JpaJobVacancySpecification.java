package com.miguel.jobnest.infrastructure.persistence.jpa.specifications;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaJobVacancyEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaJobVacancySpecification {
    public static Specification<JpaJobVacancyEntity> filterByTerms(String terms) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.or(
                        criterialBuilder.like(criterialBuilder.lower(root.get("title")), "%" + terms.toLowerCase() + "%"),
                        criterialBuilder.like(criterialBuilder.lower(root.get("description")), "%" + terms.toLowerCase() + "%")
                );
    }

    public static Specification<JpaJobVacancyEntity> filterByUserId(String userId) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<JpaJobVacancyEntity> filterByIsDeleted(boolean isDeleted) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("isDeleted"), isDeleted);
    }
}
