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
            JobVacancyRepository jobVacancyRepository,
            SubscriptionRepository subscriptionRepository,
            TransactionExecutor transactionExecutor
    ) {
        this.jobVacancyRepository = jobVacancyRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(SoftDeleteJobVacancyUseCaseInput input) {
        final JobVacancy jobVacancy = this.getJobVacancyById(input.id());

        final JobVacancy updatedJobVacancy = jobVacancy.withIsDeleted(true);

        this.transactionExecutor.runTransaction(() -> {
            final JobVacancy savedJobVacancy = this.saveJobVacancy(updatedJobVacancy);

            final List<Subscription> subscriptions = this.getAllByJobVacancyId(savedJobVacancy.getId());

            subscriptions.stream().map(subscription -> subscription.withIsCanceled(true)).forEach(this::saveSubscription);
        });
    }

    private JobVacancy getJobVacancyById(String id) {
        return this.jobVacancyRepository.findById(id).orElseThrow(() -> NotFoundException.with("Job vacancy not found"));
    }

    private JobVacancy saveJobVacancy(JobVacancy jobVacancy) {
        return this.jobVacancyRepository.save(jobVacancy);
    }

    private List<Subscription> getAllByJobVacancyId(String jobVacancyId) {
        return this.subscriptionRepository.findAllByJobVacancyId(jobVacancyId);
    }

    private void saveSubscription(Subscription subscription) {
        this.subscriptionRepository.save(subscription);
    }
}
