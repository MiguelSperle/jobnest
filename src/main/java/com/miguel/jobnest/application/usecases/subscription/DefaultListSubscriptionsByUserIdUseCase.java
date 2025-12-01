package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.usecases.subscription.ListSubscriptionsByUserIdUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.ListSubscriptionsByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

public class DefaultListSubscriptionsByUserIdUseCase implements ListSubscriptionsByUserIdUseCase {
    private final SubscriptionRepository subscriptionRepository;

    public DefaultListSubscriptionsByUserIdUseCase(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public ListSubscriptionsByUserIdUseCaseOutput execute(ListSubscriptionsByUserIdUseCaseInput input) {
        final Pagination<Subscription> paginatedSubscriptions = this.getAllPaginatedSubscriptionsByUserId(input.userId(), input.searchQuery());

        return ListSubscriptionsByUserIdUseCaseOutput.from(paginatedSubscriptions);
    }

    private Pagination<Subscription> getAllPaginatedSubscriptionsByUserId(String userId, SearchQuery searchQuery) {
        return this.subscriptionRepository.findAllPaginatedByUserId(userId, searchQuery);
    }
}
