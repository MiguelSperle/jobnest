package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.UpdateJobVacancyUseCaseInput;
import com.miguel.jobnest.domain.builders.JobVacancyBuilder;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class UpdateJobVacancyUseCaseTest {
    @InjectMocks
    private DefaultUpdateJobVacancyUseCase useCase;

    @Mock
    private JobVacancyRepository jobVacancyRepository;

    @Test
    void shouldUpdateJobVacancy_whenCallExecute() {
        final JobVacancy jobVacancy = JobVacancyBuilder.jobVacancy().id(IdentifierUtils.generateNewId())
                .title("Old title")
                .description("Old description")
                .companyName("Old company name")
                .seniorityLevel(SeniorityLevel.INTERN)
                .modality(Modality.ON_SITE)
                .build();

        final String title = "Java developer";
        final String description = "This is the job vacancy description";
        final String companyName = "Company name";

        final UpdateJobVacancyUseCaseInput input = UpdateJobVacancyUseCaseInput.with(
                jobVacancy.getId(),
                title,
                description,
                SeniorityLevel.JUNIOR.name(),
                Modality.HYBRID.name(),
                companyName
        );

        Mockito.when(this.jobVacancyRepository.findById(Mockito.any())).thenReturn(Optional.of(jobVacancy));
        Mockito.when(this.jobVacancyRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).save(Mockito.argThat(jobVacancySaved ->
                Objects.equals(jobVacancySaved.getTitle(), input.title()) &&
                        Objects.equals(jobVacancySaved.getDescription(), input.description()) &&
                        jobVacancySaved.getSeniorityLevel() == SeniorityLevel.JUNIOR &&
                        jobVacancySaved.getModality() == Modality.HYBRID &&
                        Objects.equals(jobVacancySaved.getCompanyName(), input.companyName())
        ));
    }

    @Test
    void shouldThrowNotFoundException_whenCallExecute_becauseJobVacancyDoesNotExist() {
        final String title = "Java Developer";
        final String description = "This is the job vacancy description";
        final String companyName = "Company Name";

        final UpdateJobVacancyUseCaseInput input = UpdateJobVacancyUseCaseInput.with(
                IdentifierUtils.generateNewId(),
                title,
                description,
                SeniorityLevel.JUNIOR.name(),
                Modality.REMOTE.name(),
                companyName
        );

        Mockito.when(this.jobVacancyRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final var ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "Job vacancy not found";

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
