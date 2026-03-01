package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.testsupport.builders.entities.domain.JobVacancyTestBuilder;
import com.miguel.jobnest.testsupport.builders.entities.jpa.JpaJobVacancyEntityTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class JpaJobVacancyEntityTest {
    @Test
    void shouldReturnJpaJobVacancyEntity_whenCallToEntity() {
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(IdentifierUtils.generateNewId()).build();

        final JpaJobVacancyEntity transformedIntoJpaEntity = JpaJobVacancyEntity.toEntity(jobVacancy);

        Assertions.assertNotNull(transformedIntoJpaEntity);
        Assertions.assertEquals(jobVacancy.getId(), transformedIntoJpaEntity.getId());
        Assertions.assertEquals(jobVacancy.getUserId(), transformedIntoJpaEntity.getUserId());
        Assertions.assertEquals(jobVacancy.getTitle(), transformedIntoJpaEntity.getTitle());
        Assertions.assertEquals(jobVacancy.getDescription(), transformedIntoJpaEntity.getDescription());
        Assertions.assertEquals(jobVacancy.getSeniorityLevel(), transformedIntoJpaEntity.getSeniorityLevel());
        Assertions.assertEquals(jobVacancy.getModality(), transformedIntoJpaEntity.getModality());
        Assertions.assertEquals(jobVacancy.getCompanyName(), transformedIntoJpaEntity.getCompanyName());
        Assertions.assertEquals(jobVacancy.getIsDeleted(), transformedIntoJpaEntity.getIsDeleted());
        Assertions.assertEquals(jobVacancy.getCreatedAt(), transformedIntoJpaEntity.getCreatedAt());
    }

    @Test
    void shouldReturnJobVacancy_whenCallToDomain() {
        final JpaJobVacancyEntity jpaJobVacancyEntity = JpaJobVacancyEntityTestBuilder.aJpaJobVacancyEntity().build();

        final JobVacancy transformedIntoDomainEntity = jpaJobVacancyEntity.toDomain();

        Assertions.assertNotNull(transformedIntoDomainEntity);
        Assertions.assertEquals(jpaJobVacancyEntity.getId(), transformedIntoDomainEntity.getId());
        Assertions.assertEquals(jpaJobVacancyEntity.getUserId(), transformedIntoDomainEntity.getUserId());
        Assertions.assertEquals(jpaJobVacancyEntity.getTitle(), transformedIntoDomainEntity.getTitle());
        Assertions.assertEquals(jpaJobVacancyEntity.getDescription(), transformedIntoDomainEntity.getDescription());
        Assertions.assertEquals(jpaJobVacancyEntity.getSeniorityLevel(), transformedIntoDomainEntity.getSeniorityLevel());
        Assertions.assertEquals(jpaJobVacancyEntity.getModality(), transformedIntoDomainEntity.getModality());
        Assertions.assertEquals(jpaJobVacancyEntity.getCompanyName(), transformedIntoDomainEntity.getCompanyName());
        Assertions.assertEquals(jpaJobVacancyEntity.getIsDeleted(), transformedIntoDomainEntity.getIsDeleted());
        Assertions.assertEquals(jpaJobVacancyEntity.getCreatedAt(), transformedIntoDomainEntity.getCreatedAt());
    }

    @Test
    void shouldReturnJpaJobVacancyEntity_whenCallWith() {
        final String id = IdentifierUtils.generateNewId();
        final String userId = IdentifierUtils.generateNewId();
        final String title =  "Some title";
        final String description = "Some description";
        final SeniorityLevel seniorityLevel = SeniorityLevel.JUNIOR;
        final Modality modality = Modality.REMOTE;
        final String companyName = "Company name";
        final boolean isDeleted = false;
        final LocalDateTime createdAt = TimeUtils.now();

        final JpaJobVacancyEntity jpaJobVacancyEntity = JpaJobVacancyEntity.with(
                id,
                userId,
                title,
                description,
                seniorityLevel,
                modality,
                companyName,
                isDeleted,
                createdAt
        );

        Assertions.assertNotNull(jpaJobVacancyEntity);
        Assertions.assertEquals(id, jpaJobVacancyEntity.getId());
        Assertions.assertEquals(userId, jpaJobVacancyEntity.getUserId());
        Assertions.assertEquals(title, jpaJobVacancyEntity.getTitle());
        Assertions.assertEquals(description, jpaJobVacancyEntity.getDescription());
        Assertions.assertEquals(seniorityLevel, jpaJobVacancyEntity.getSeniorityLevel());
        Assertions.assertEquals(modality, jpaJobVacancyEntity.getModality());
        Assertions.assertEquals(companyName, jpaJobVacancyEntity.getCompanyName());
        Assertions.assertEquals(isDeleted, jpaJobVacancyEntity.getIsDeleted());
        Assertions.assertEquals(createdAt, jpaJobVacancyEntity.getCreatedAt());
    }
}
