package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
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
import com.miguel.jobnest.utils.JobVacancyTestBuilder;
import com.miguel.jobnest.utils.SubscriptionTestBuilder;
import com.miguel.jobnest.utils.UserTestBuilder;
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
        final User userCandidate = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final User userRecruiter = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(userRecruiter.getId()).build();
        final Subscription subscription = SubscriptionTestBuilder.aSubscription().userId(userCandidate.getId()).jobVacancyId(jobVacancy.getId()).build();
        final List<Subscription> subscriptions = List.of(subscription);
        final int page = 0;
        final int perPage = 10;
        final String sort = "createdAt";
        final String direction = "desc";

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(page, perPage, sort, direction);

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
        Assertions.assertEquals(paginatedSubscriptions.paginationMetadata(), output.paginatedSubscriptions().paginationMetadata());
        Assertions.assertEquals(paginatedSubscriptions.items(), output.paginatedSubscriptions().items());

        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).findAllPaginatedByJobVacancyId(Mockito.any(), Mockito.any());
    }
}
