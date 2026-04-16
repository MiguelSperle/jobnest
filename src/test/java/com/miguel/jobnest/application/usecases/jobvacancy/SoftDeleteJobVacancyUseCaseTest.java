package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionManager;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.SoftDeleteJobVacancyUseCaseInput;
import com.miguel.jobnest.domain.builders.JobVacancyBuilder;
import com.miguel.jobnest.domain.builders.SubscriptionBuilder;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class SoftDeleteJobVacancyUseCaseTest {
    @InjectMocks
    private DefaultSoftDeleteJobVacancyUseCase useCase;

    @Mock
    private JobVacancyRepository jobVacancyRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private TransactionManager transactionManager;

    @Test
    void shouldDeleteJobVacancy_whenCallExecute() {
        final JobVacancy jobVacancy = JobVacancyBuilder.jobVacancy().id(IdentifierUtils.generateNewId()).build();
        final Subscription subscription = SubscriptionBuilder.subscription().jobVacancyId(jobVacancy.getId()).build();

        final SoftDeleteJobVacancyUseCaseInput input = SoftDeleteJobVacancyUseCaseInput.with(
                jobVacancy.getId()
        );

        Mockito.when(this.jobVacancyRepository.findById(Mockito.any())).thenReturn(Optional.of(jobVacancy));
        Mockito.when(this.subscriptionRepository.findAllByJobVacancyId(Mockito.any())).thenReturn(List.of(subscription));
        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionManager).runTransaction(Mockito.any());
        Mockito.when(this.jobVacancyRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());
        Mockito.when(this.subscriptionRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).findAllByJobVacancyId(Mockito.any());
        Mockito.verify(this.transactionManager, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).save(Mockito.argThat(JobVacancy::getIsDeleted));
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).save(Mockito.argThat(Subscription::getIsCanceled));
    }

    @Test
    void shouldThrowNotFoundException_whenCallExecute_becauseJobVacancyDoesNotExist() {
        final SoftDeleteJobVacancyUseCaseInput input = SoftDeleteJobVacancyUseCaseInput.with(
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
