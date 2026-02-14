package com.miguel.jobnest.infrastructure.configurations.usecases;

import com.miguel.jobnest.application.abstractions.repositories.EventOutboxRepository;
import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.services.UploadService;
import com.miguel.jobnest.application.abstractions.usecases.subscription.*;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.usecases.subscription.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionUseCasesConfiguration {
    @Bean
    public CreateSubscriptionUseCase createSubscriptionUseCase(
            SubscriptionRepository subscriptionRepository,
            UploadService uploadService,
            SecurityService securityService,
            EventOutboxRepository eventOutboxRepository,
            TransactionExecutor transactionExecutor
    ) {
        return new DefaultCreateSubscriptionUseCase(
                subscriptionRepository,
                uploadService,
                securityService,
                eventOutboxRepository,
                transactionExecutor
        );
    }

    @Bean
    public ListSubscriptionsByUserIdUseCase listSubscriptionsByUserIdUseCase(
            SubscriptionRepository subscriptionRepository,
            SecurityService securityService
    ) {
        return new DefaultListSubscriptionsByUserIdUseCase(
                subscriptionRepository,
                securityService
        );
    }

    @Bean
    public CancelSubscriptionUseCase cancelSubscriptionUseCase(SubscriptionRepository subscriptionRepository) {
        return new DefaultCancelSubscriptionUseCase(subscriptionRepository);
    }

    @Bean
    public ListSubscriptionsByJobVacancyIdUseCase listSubscriptionsByJobVacancyIdUseCase(SubscriptionRepository subscriptionRepository) {
        return new DefaultListSubscriptionsByJobVacancyIdUseCase(subscriptionRepository);
    }

    @Bean
    public GetSubscriptionByIdUseCase getSubscriptionByIdUseCase(SubscriptionRepository subscriptionRepository) {
        return new DefaultGetSubscriptionByIdUseCase(subscriptionRepository);
    }
}
