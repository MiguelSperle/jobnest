package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.SoftDeleteJobVacancyUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.SoftDeleteJobVacancyUseCaseInput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

import java.util.List;

public class DefaultSoftDeleteJobVacancyUseCase implements SoftDeleteJobVacancyUseCase {
    private final JobVacancyRepository jobVacancyRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TransactionExecutor transactionExecutor;

    public DefaultSoftDeleteJobVacancyUseCase(
            final JobVacancyRepository jobVacancyRepository,
            final SubscriptionRepository subscriptionRepository,
            final TransactionExecutor transactionExecutor
    ) {
        this.jobVacancyRepository = jobVacancyRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(final SoftDeleteJobVacancyUseCaseInput input) {
        final JobVacancy jobVacancy = this.getJobVacancyById(input.jobVacancyId());

        final List<Subscription> subscriptions = this.getAllByJobVacancyId(jobVacancy.getId());

        final JobVacancy updatedJobVacancy = jobVacancy.withIsDeleted(true);

        this.transactionExecutor.runTransaction(() -> {
            this.saveJobVacancy(updatedJobVacancy);
            subscriptions.stream().map(subscription -> subscription.withIsCanceled(true)).forEach(this::saveSubscription);
        });
    }

    private JobVacancy getJobVacancyById(final String id) {
        return this.jobVacancyRepository.findById(id).orElseThrow(() -> NotFoundException.with("Job vacancy not found"));
    }

    private void saveJobVacancy(final JobVacancy jobVacancy) {
        this.jobVacancyRepository.save(jobVacancy);
    }

    private List<Subscription> getAllByJobVacancyId(final String jobVacancyId) {
        return this.subscriptionRepository.findAllByJobVacancyId(jobVacancyId);
    }

    private void saveSubscription(final Subscription subscription) {
        this.subscriptionRepository.save(subscription);
    }
}
