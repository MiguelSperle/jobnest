package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.testsupport.builders.domain.JobVacancyTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class JobVacancyTest {

    @Test
    void shouldReturnJobVacancy_whenCallNewJobVacancy() {
        final String userId = IdentifierUtils.generateNewId();
        final String title = "Java Developer";
        final String description = "This is job vacancy description";
        final SeniorityLevel seniorityLevel = SeniorityLevel.JUNIOR;
        final Modality modality = Modality.REMOTE;
        final String companyName = "Company name";

        final JobVacancy newJobVacancy = JobVacancy.newJobVacancy(
                userId,
                title,
                description,
                seniorityLevel,
                modality,
                companyName
        );

        Assertions.assertNotNull(newJobVacancy);
        Assertions.assertNotNull(newJobVacancy.getId());
        Assertions.assertEquals(userId, newJobVacancy.getUserId());
        Assertions.assertEquals(title, newJobVacancy.getTitle());
        Assertions.assertEquals(description, newJobVacancy.getDescription());
        Assertions.assertEquals(seniorityLevel, newJobVacancy.getSeniorityLevel());
        Assertions.assertEquals(modality, newJobVacancy.getModality());
        Assertions.assertEquals(companyName, newJobVacancy.getCompanyName());
        Assertions.assertFalse(newJobVacancy.getIsDeleted());
        Assertions.assertNotNull(newJobVacancy.getCreatedAt());
    }

    @Test
    void shouldReturnJobVacancy_whenCallWith() {
        final String id = IdentifierUtils.generateNewId();
        final String userId = IdentifierUtils.generateNewId();
        final String title = "Java Developer";
        final String description = "This is job vacancy description";
        final SeniorityLevel seniorityLevel = SeniorityLevel.JUNIOR;
        final Modality modality = Modality.REMOTE;
        final String companyName = "Company name";
        final boolean isDeleted = false;
        final LocalDateTime createdAt = TimeUtils.now();

        final JobVacancy jobVacancy = JobVacancy.with(
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

        Assertions.assertNotNull(jobVacancy);
        Assertions.assertEquals(id, jobVacancy.getId());
        Assertions.assertEquals(userId, jobVacancy.getUserId());
        Assertions.assertEquals(title, jobVacancy.getTitle());
        Assertions.assertEquals(description, jobVacancy.getDescription());
        Assertions.assertEquals(seniorityLevel, jobVacancy.getSeniorityLevel());
        Assertions.assertEquals(modality, jobVacancy.getModality());
        Assertions.assertEquals(companyName, jobVacancy.getCompanyName());
        Assertions.assertEquals(isDeleted, jobVacancy.getIsDeleted());
        Assertions.assertEquals(createdAt, jobVacancy.getCreatedAt());
    }

    @Test
    void shouldReturnUpdatedJobVacancy_whenCallWithTitle() {
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(IdentifierUtils.generateNewId()).build();

        final String newTitle = "PHP Developer";

        final JobVacancy updatedJobVacancy = jobVacancy.withTitle(newTitle);

        Assertions.assertNotNull(updatedJobVacancy);
        Assertions.assertEquals(newTitle, updatedJobVacancy.getTitle());
    }

    @Test
    void shouldReturnUpdatedJobVacancy_whenCallWithDescription() {
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(IdentifierUtils.generateNewId()).build();

        final String newDescription = "This is the new job vacancy description";

        final JobVacancy updatedJobVacancy = jobVacancy.withDescription(newDescription);

        Assertions.assertNotNull(updatedJobVacancy);
        Assertions.assertEquals(newDescription, updatedJobVacancy.getDescription());
    }

    @Test
    void shouldReturnUpdatedJobVacancy_whenCallWithSeniorityLevel() {
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(IdentifierUtils.generateNewId()).build();

        final SeniorityLevel newSeniorityLevel = SeniorityLevel.INTERMEDIATE;

        final JobVacancy updatedJobVacancy = jobVacancy.withSeniorityLevel(newSeniorityLevel);

        Assertions.assertNotNull(updatedJobVacancy);
        Assertions.assertEquals(newSeniorityLevel, updatedJobVacancy.getSeniorityLevel());
    }

    @Test
    void shouldReturnUpdatedJobVacancy_whenCallWithModality() {
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(IdentifierUtils.generateNewId()).build();

        final Modality newModality = Modality.HYBRID;

        final JobVacancy updatedJobVacancy = jobVacancy.withModality(newModality);

        Assertions.assertNotNull(updatedJobVacancy);
        Assertions.assertEquals(newModality, updatedJobVacancy.getModality());
    }

    @Test
    void shouldReturnUpdatedJobVacancy_whenCallWithCompanyName() {
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(IdentifierUtils.generateNewId()).build();

        final String newCompanyName = "New company name";

        final JobVacancy updatedJobVacancy = jobVacancy.withCompanyName(newCompanyName);

        Assertions.assertNotNull(updatedJobVacancy);
        Assertions.assertEquals(newCompanyName, updatedJobVacancy.getCompanyName());
    }

    @Test
    void shouldReturnUpdatedJobVacancy_whenCallWithIsDeleted() {
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(IdentifierUtils.generateNewId()).build();

        final JobVacancy updatedJobVacancy = jobVacancy.withIsDeleted(true);

        Assertions.assertNotNull(updatedJobVacancy);
        Assertions.assertEquals(true, updatedJobVacancy.getIsDeleted());
    }

    @Test
    void shouldReturnFormattedJobVacancy_whenCallToString() {
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(IdentifierUtils.generateNewId()).build();

        final String expectedToString = "JobVacancy{" +
                "id='" + jobVacancy.getId() + '\'' +
                ", userId='" + jobVacancy.getUserId() + '\'' +
                ", title='" + jobVacancy.getTitle() + '\'' +
                ", description='" + jobVacancy.getDescription() + '\'' +
                ", seniorityLevel=" + jobVacancy.getSeniorityLevel() +
                ", modality=" + jobVacancy.getModality() +
                ", companyName='" + jobVacancy.getCompanyName() + '\'' +
                ", isDeleted='" + jobVacancy.getIsDeleted() + '\'' +
                ", createdAt=" + jobVacancy.getCreatedAt() +
                '}';

        Assertions.assertEquals(expectedToString, jobVacancy.toString());
    }
}
