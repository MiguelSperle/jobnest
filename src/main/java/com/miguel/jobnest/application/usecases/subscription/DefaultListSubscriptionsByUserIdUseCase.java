package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.usecases.subscription.ListSubscriptionsByUserIdUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.ListSubscriptionsByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

public class DefaultListSubscriptionsByUserIdUseCase implements ListSubscriptionsByUserIdUseCase {
    private final SubscriptionRepository subscriptionRepository;
    private final SecurityService securityService;

    public DefaultListSubscriptionsByUserIdUseCase(
            final SubscriptionRepository subscriptionRepository,
            final SecurityService securityService
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.securityService = securityService;
    }

    @Override
    public ListSubscriptionsByUserIdUseCaseOutput execute(final ListSubscriptionsByUserIdUseCaseInput input) {
        final String authenticatedUserId = this.securityService.getPrincipal();

        final Pagination<Subscription> paginatedSubscriptions = this.getAllPaginatedSubscriptionsByUserId(authenticatedUserId, input.searchQuery());

        return ListSubscriptionsByUserIdUseCaseOutput.from(paginatedSubscriptions);
    }

    private Pagination<Subscription> getAllPaginatedSubscriptionsByUserId(final String userId, final SearchQuery searchQuery) {
        return this.subscriptionRepository.findAllPaginatedByUserId(userId, searchQuery);
    }
}
