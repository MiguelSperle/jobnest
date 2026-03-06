package com.miguel.jobnest.infrastructure.configurations.usecases;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.services.UploadService;
import com.miguel.jobnest.application.abstractions.usecases.subscription.*;
import com.miguel.jobnest.application.usecases.subscription.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionUseCasesConfiguration {
    @Bean
    public CreateSubscriptionUseCase createSubscriptionUseCase(
            final SubscriptionRepository subscriptionRepository,
            final UploadService uploadService,
            final SecurityService securityService
    ) {
        return new DefaultCreateSubscriptionUseCase(
                subscriptionRepository,
                uploadService,
                securityService
        );
    }

    @Bean
    public ListSubscriptionsByUserIdUseCase listSubscriptionsByUserIdUseCase(
            final SubscriptionRepository subscriptionRepository,
            final SecurityService securityService
    ) {
        return new DefaultListSubscriptionsByUserIdUseCase(
                subscriptionRepository,
                securityService
        );
    }

    @Bean
    public CancelSubscriptionUseCase cancelSubscriptionUseCase(final SubscriptionRepository subscriptionRepository) {
        return new DefaultCancelSubscriptionUseCase(subscriptionRepository);
    }

    @Bean
    public ListSubscriptionsByJobVacancyIdUseCase listSubscriptionsByJobVacancyIdUseCase(final SubscriptionRepository subscriptionRepository) {
        return new DefaultListSubscriptionsByJobVacancyIdUseCase(subscriptionRepository);
    }

    @Bean
    public GetSubscriptionByIdUseCase getSubscriptionByIdUseCase(final SubscriptionRepository subscriptionRepository) {
        return new DefaultGetSubscriptionByIdUseCase(subscriptionRepository);
    }
}
