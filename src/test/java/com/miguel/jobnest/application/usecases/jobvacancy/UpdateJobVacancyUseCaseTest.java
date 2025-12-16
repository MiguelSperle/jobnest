package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.UpdateJobVacancyUseCaseInput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.utils.JobVacancyTestBuilder;
import com.miguel.jobnest.utils.UserTestBuilder;
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
    void shouldUpdateJobVacancy() {
        final User userRecruiter = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(userRecruiter.getId()).build();
        final String title = "Java Developer";
        final String description = "This is the job vacancy description";
        final String companyName = "Company Name";

        final UpdateJobVacancyUseCaseInput input = UpdateJobVacancyUseCaseInput.with(
                jobVacancy.getId(),
                title,
                description,
                SeniorityLevel.JUNIOR.name(),
                Modality.REMOTE.name(),
                companyName
        );

        Mockito.when(this.jobVacancyRepository.findById(Mockito.any())).thenReturn(Optional.of(jobVacancy));
        Mockito.when(this.jobVacancyRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).save(Mockito.argThat(jobVacancySaved ->
                Objects.equals(jobVacancySaved.getTitle(), input.title()) &&
                        Objects.equals(jobVacancySaved.getDescription(), input.description()) &&
                        Objects.equals(jobVacancySaved.getSeniorityLevel(), SeniorityLevel.JUNIOR) &&
                        Objects.equals(jobVacancySaved.getModality(), Modality.REMOTE) &&
                        Objects.equals(jobVacancySaved.getCompanyName(), input.companyName())
        ));
    }

    @Test
    void shouldThrowNotFoundException_whenJobVacancyDoesNotExist() {
        final String title = "Java Developer";
        final String description = "This is the job vacancy description";
        final String companyName = "Company Name";

        final UpdateJobVacancyUseCaseInput input = UpdateJobVacancyUseCaseInput.with(
                IdentifierUtils.generateUUID(),
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
