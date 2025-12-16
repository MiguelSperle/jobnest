package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.CreateJobVacancyUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.utils.UserTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CreateJobVacancyUseCaseTest {
    @InjectMocks
    private DefaultCreateJobVacancyUseCase useCase;

    @Mock
    private JobVacancyRepository jobVacancyRepository;

    @Mock
    private SecurityService securityService;

    @Test
    void shouldCreateJobVacancy() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final String title = "Java Developer";
        final String description = "This is the job vacancy description";
        final String companyName = "Company Name";

        final CreateJobVacancyUseCaseInput input = CreateJobVacancyUseCaseInput.with(
                title,
                description,
                SeniorityLevel.JUNIOR.name(),
                Modality.REMOTE.name(),
                companyName
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(user.getId());
        Mockito.when(this.jobVacancyRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).save(Mockito.argThat(jobVacancySaved ->
                Objects.nonNull(jobVacancySaved.getId()) &&
                        Objects.equals(jobVacancySaved.getUserId(), user.getId()) &&
                        Objects.equals(jobVacancySaved.getTitle(), input.title()) &&
                        Objects.equals(jobVacancySaved.getDescription(), input.description()) &&
                        Objects.equals(jobVacancySaved.getSeniorityLevel(), SeniorityLevel.JUNIOR) &&
                        Objects.equals(jobVacancySaved.getModality(), Modality.REMOTE) &&
                        Objects.equals(jobVacancySaved.getCompanyName(), input.companyName()) &&
                        Objects.equals(jobVacancySaved.getIsDeleted(), false) &&
                        Objects.nonNull(jobVacancySaved.getCreatedAt())
        ));
    }
}
