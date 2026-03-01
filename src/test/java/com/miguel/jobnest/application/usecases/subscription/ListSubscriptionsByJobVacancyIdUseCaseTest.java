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
import com.miguel.jobnest.application.builders.JobVacancyTestBuilder;
import com.miguel.jobnest.application.builders.SubscriptionTestBuilder;
import com.miguel.jobnest.application.builders.UserTestBuilder;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
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
    void shouldListSubscriptionsByJobVacancyId_whenCallExecute() {
        final User userCandidate = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final User userRecruiter = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(userRecruiter.getId()).build();
        final Subscription subscription = SubscriptionTestBuilder.aSubscription().userId(userCandidate.getId()).jobVacancyId(jobVacancy.getId()).build();
        final List<Subscription> subscriptions = List.of(subscription);
        final int page = 0;
        final int perPage = 10;
        final String sort = "createdAt";
        final String direction = "desc";
        final int totalPages = 1;

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(page, perPage, sort, direction);

        final PaginationMetadata metadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), totalPages, subscriptions.size()
        );

        final ListSubscriptionsByJobVacancyIdUseCaseInput input = ListSubscriptionsByJobVacancyIdUseCaseInput.with(
                jobVacancy.getId(),
                searchQuery
        );

        final Pagination<Subscription> paginatedSubscriptions = new Pagination<>(
                metadata, subscriptions
        );

        Mockito.when(this.subscriptionRepository.findAllPaginatedByJobVacancyId(Mockito.any(), Mockito.any())).thenReturn(paginatedSubscriptions);

        final ListSubscriptionsByJobVacancyIdUseCaseOutput output = this.useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.paginatedSubscriptions());
        Assertions.assertEquals(paginatedSubscriptions.metadata(), output.paginatedSubscriptions().metadata());
        Assertions.assertEquals(paginatedSubscriptions.items(), output.paginatedSubscriptions().items());
        Assertions.assertEquals(subscriptions.size(), output.paginatedSubscriptions().metadata().totalItems());

        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).findAllPaginatedByJobVacancyId(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldListEmptySubscriptionsByJobVacancyId_whenCallExecute() {
        final int page = 0;
        final int perPage = 10;
        final String sort = "createdAt";
        final String direction = "desc";
        final int totalPages = 1;

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(page, perPage, sort, direction);

        final PaginationMetadata metadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), totalPages, 0
        );

        final ListSubscriptionsByJobVacancyIdUseCaseInput input = ListSubscriptionsByJobVacancyIdUseCaseInput.with(
                IdentifierUtils.generateNewId(),
                searchQuery
        );

        final Pagination<Subscription> paginatedSubscriptions = new Pagination<>(
                metadata, List.of()
        );

        Mockito.when(this.subscriptionRepository.findAllPaginatedByJobVacancyId(Mockito.any(), Mockito.any())).thenReturn(paginatedSubscriptions);

        final ListSubscriptionsByJobVacancyIdUseCaseOutput output = this.useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.paginatedSubscriptions());
        Assertions.assertEquals(paginatedSubscriptions.metadata(), output.paginatedSubscriptions().metadata());
        Assertions.assertTrue(output.paginatedSubscriptions().items().isEmpty());
        Assertions.assertEquals(0, output.paginatedSubscriptions().metadata().totalItems());
    }
}
