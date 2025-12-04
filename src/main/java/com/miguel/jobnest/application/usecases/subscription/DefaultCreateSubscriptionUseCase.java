package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.services.UploadService;
import com.miguel.jobnest.application.abstractions.usecases.subscription.CreateSubscriptionUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.CreateSubscriptionUseCaseInput;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.events.SubscriptionCreatedEvent;
import com.miguel.jobnest.domain.exceptions.DomainException;

public class DefaultCreateSubscriptionUseCase implements CreateSubscriptionUseCase {
    private final SubscriptionRepository subscriptionRepository;
    private final UploadService uploadService;
    private final SecurityService securityService;
    private final MessageProducer messageProducer;

    private static final String SUBSCRIPTION_CREATED_EXCHANGE = "subscription.created.exchange";
    private static final String SUBSCRIPTION_CREATED_ROUTING_KEY = "subscription.created.routing.key";

    public DefaultCreateSubscriptionUseCase(
            SubscriptionRepository subscriptionRepository,
            UploadService uploadService,
            SecurityService securityService,
            MessageProducer messageProducer
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.uploadService = uploadService;
        this.securityService = securityService;
        this.messageProducer = messageProducer;
    }

    @Override
    public void execute(CreateSubscriptionUseCaseInput input) {
        final String authenticatedUserId = this.securityService.getPrincipal();

        if (this.verifySubscriptionAlreadyExistsByUserIdAndJobVacancyId(authenticatedUserId, input.jobVacancyId())) {
            throw DomainException.with("You are already subscribed in this job vacancy", 409);
        }

        String resumeUrl = null;

        try {
            resumeUrl = this.uploadService.uploadFile(input.bytesFile(), "resume-file", "image");

            final Subscription newSubscription = Subscription.newSubscription(authenticatedUserId, input.jobVacancyId(), resumeUrl);

            final Subscription savedSubscription = this.saveSubscription(newSubscription);

            final SubscriptionCreatedEvent event = SubscriptionCreatedEvent.from(
                    savedSubscription.getUserId(),
                    savedSubscription.getJobVacancyId()
            );

            this.messageProducer.publish(SUBSCRIPTION_CREATED_EXCHANGE, SUBSCRIPTION_CREATED_ROUTING_KEY, event);
        } catch (Exception ex) {
            if (resumeUrl != null) {
                final String publicId = this.uploadService.extractPublicId(resumeUrl, "resume-file");
                this.uploadService.destroyFile(publicId, "image");
            }
            throw ex;
        }
    }

    private boolean verifySubscriptionAlreadyExistsByUserIdAndJobVacancyId(String userId, String jobVacancyId) {
        return this.subscriptionRepository.existsByUserIdAndJobVacancyId(userId, jobVacancyId);
    }

    private Subscription saveSubscription(Subscription subscription) {
        return this.subscriptionRepository.save(subscription);
    }
}
