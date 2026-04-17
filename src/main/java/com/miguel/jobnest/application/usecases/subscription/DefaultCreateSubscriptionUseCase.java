package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.services.UploadService;
import com.miguel.jobnest.application.abstractions.usecases.subscription.CreateSubscriptionUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.CreateSubscriptionUseCaseInput;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.events.SubscriptionCreatedEvent;
import com.miguel.jobnest.domain.exceptions.DomainException;

public class DefaultCreateSubscriptionUseCase implements CreateSubscriptionUseCase {
    private final SubscriptionRepository subscriptionRepository;
    private final UploadService uploadService;

    public DefaultCreateSubscriptionUseCase(
            final SubscriptionRepository subscriptionRepository,
            final UploadService uploadService
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.uploadService = uploadService;
    }

    @Override
    public void execute(final CreateSubscriptionUseCaseInput input) {
        if (this.verifySubscriptionAlreadyExistsByUserIdAndJobVacancyId(input.userId(), input.jobVacancyId())) {
            throw DomainException.with("You are already subscribed in this job vacancy", 409);
        }

        String resumeUrl = null;

        try {
            resumeUrl = this.uploadService.uploadFile(input.bytesFile(), "resume-file", "image");

            final Subscription newSubscription = Subscription.newSubscription(input.userId(), input.jobVacancyId(), resumeUrl);

            newSubscription.registerEvent(new SubscriptionCreatedEvent(
                    newSubscription.getId(),
                    newSubscription.getUserId(),
                    newSubscription.getJobVacancyId()
            ));

            this.saveSubscription(newSubscription);
        } catch (final Exception ex) {
            if (resumeUrl != null) {
                final String publicId = resumeUrl.substring(resumeUrl.indexOf("resume-file/"), resumeUrl.lastIndexOf("."));
                this.uploadService.destroyFile(publicId, "image");
            }
            throw ex;
        }
    }

    private boolean verifySubscriptionAlreadyExistsByUserIdAndJobVacancyId(final String userId, final String jobVacancyId) {
        return this.subscriptionRepository.existsByUserIdAndJobVacancyId(userId, jobVacancyId);
    }

    private void saveSubscription(final Subscription subscription) {
        this.subscriptionRepository.save(subscription);
    }
}
