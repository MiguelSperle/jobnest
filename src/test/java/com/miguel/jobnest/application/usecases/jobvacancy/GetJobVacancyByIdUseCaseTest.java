package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.GetJobVacancyByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.GetJobVacancyByIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.application.utils.JobVacancyTestBuilder;
import com.miguel.jobnest.application.utils.UserTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetJobVacancyByIdUseCaseTest {
    @InjectMocks
    private DefaultGetJobVacancyByIdUseCase useCase;

    @Mock
    private JobVacancyRepository jobVacancyRepository;

    @Test
    void shouldGetJobVacancyById() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(user.getId()).build();

        final GetJobVacancyByIdUseCaseInput input = GetJobVacancyByIdUseCaseInput.with(
                jobVacancy.getId()
        );

        Mockito.when(this.jobVacancyRepository.findById(Mockito.any())).thenReturn(Optional.of(jobVacancy));

        final GetJobVacancyByIdUseCaseOutput output = this.useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.jobVacancy());
        Assertions.assertEquals(jobVacancy.getId(), output.jobVacancy().getId());
        Assertions.assertEquals(jobVacancy.getUserId(), output.jobVacancy().getUserId());
        Assertions.assertEquals(jobVacancy.getTitle(), output.jobVacancy().getTitle());
        Assertions.assertEquals(jobVacancy.getDescription(), output.jobVacancy().getDescription());
        Assertions.assertEquals(jobVacancy.getSeniorityLevel(), output.jobVacancy().getSeniorityLevel());
        Assertions.assertEquals(jobVacancy.getModality(), output.jobVacancy().getModality());
        Assertions.assertEquals(jobVacancy.getCompanyName(), output.jobVacancy().getCompanyName());
        Assertions.assertEquals(jobVacancy.getIsDeleted(), output.jobVacancy().getIsDeleted());
        Assertions.assertEquals(jobVacancy.getCreatedAt(), output.jobVacancy().getCreatedAt());

        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenJobVacancyDoesNotExist() {
        final GetJobVacancyByIdUseCaseInput input = GetJobVacancyByIdUseCaseInput.with(
                IdentifierUtils.generateNewId()
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
