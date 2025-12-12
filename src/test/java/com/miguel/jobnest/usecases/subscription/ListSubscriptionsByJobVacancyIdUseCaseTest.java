package com.miguel.jobnest.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.usecases.subscription.DefaultListSubscriptionsByJobVacancyIdUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.ListSubscriptionsByJobVacancyIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByJobVacancyIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.PaginationMetadata;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.utils.JobVacancyBuilderTest;
import com.miguel.jobnest.utils.SubscriptionBuilderTest;
import com.miguel.jobnest.utils.UserBuilderTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListSubscriptionsByJobVacancyIdUseCaseTest {
    @InjectMocks
    private DefaultListSubscriptionsByJobVacancyIdUseCase useCase;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Test
    void shouldListSubscriptionsByJobVacancyId() {
        final User userCandidate = UserBuilderTest.build(UserStatus.VERIFIED, AuthorizationRole.CANDIDATE);
        final User userRecruiter = UserBuilderTest.build(UserStatus.VERIFIED, AuthorizationRole.RECRUITER);
        final JobVacancy jobVacancy = JobVacancyBuilderTest.build(userRecruiter.getId());
        final Subscription subscription = SubscriptionBuilderTest.build(userCandidate.getId(), jobVacancy.getId());
        final List<Subscription> subscriptions = List.of(subscription);

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(0, 10, "createdAt", "desc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, subscriptions.size()
        );

        final ListSubscriptionsByJobVacancyIdUseCaseInput input = ListSubscriptionsByJobVacancyIdUseCaseInput.with(
                jobVacancy.getId(),
                searchQuery
        );

        final Pagination<Subscription> paginatedSubscriptions = new Pagination<>(
                paginationMetadata, subscriptions
        );

        Mockito.when(this.subscriptionRepository.findAllPaginatedByJobVacancyId(Mockito.any(), Mockito.any())).thenReturn(paginatedSubscriptions);

        final ListSubscriptionsByJobVacancyIdUseCaseOutput output = this.useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.paginatedSubscriptions());
        Assertions.assertEquals(paginatedSubscriptions, output.paginatedSubscriptions());
        Assertions.assertEquals(1, output.paginatedSubscriptions().items().size());
        Assertions.assertEquals(paginationMetadata, output.paginatedSubscriptions().paginationMetadata());

        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).findAllPaginatedByJobVacancyId(Mockito.any(), Mockito.any());
    }
}
