package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.usecases.subscription.ListSubscriptionsByJobVacancyIdUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.ListSubscriptionsByJobVacancyIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByJobVacancyIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

public class DefaultListSubscriptionsByJobVacancyIdUseCase implements ListSubscriptionsByJobVacancyIdUseCase {
    private final SubscriptionRepository subscriptionRepository;

    public DefaultListSubscriptionsByJobVacancyIdUseCase(final SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public ListSubscriptionsByJobVacancyIdUseCaseOutput execute(final ListSubscriptionsByJobVacancyIdUseCaseInput input) {
        final Pagination<Subscription> paginatedSubscriptions = this.getAllPaginatedSubscriptionsByJobVacancyId(input.jobVacancyId(), input.searchQuery());

        return ListSubscriptionsByJobVacancyIdUseCaseOutput.from(paginatedSubscriptions);
    }

    private Pagination<Subscription> getAllPaginatedSubscriptionsByJobVacancyId(final String jobVacancyId, final SearchQuery searchQuery) {
        return this.subscriptionRepository.findAllPaginatedByJobVacancyId(jobVacancyId, searchQuery);
    }
}
