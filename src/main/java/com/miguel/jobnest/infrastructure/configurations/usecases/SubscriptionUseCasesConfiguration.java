package com.miguel.jobnest.infrastructure.configurations.usecases;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.services.UploadService;
import com.miguel.jobnest.application.abstractions.usecases.subscription.*;
import com.miguel.jobnest.application.usecases.subscription.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionUseCasesConfiguration {
    @Bean
    public CreateSubscriptionUseCase createSubscriptionUseCase(
            SubscriptionRepository subscriptionRepository,
            UploadService uploadService,
            MessageProducer messageProducer
    ) {
        return new DefaultCreateSubscriptionUseCase(
                subscriptionRepository,
                uploadService,
                messageProducer
        );
    }

    @Bean
    public ListSubscriptionsByUserIdUseCase listSubscriptionsByUserIdUseCase(SubscriptionRepository subscriptionRepository) {
        return new DefaultListSubscriptionsByUserIdUseCase(subscriptionRepository);
    }

    @Bean
    public UpdateSubscriptionUseCase updateSubscriptionUseCase(SubscriptionRepository subscriptionRepository) {
        return new DefaultUpdateSubscriptionUseCase(subscriptionRepository);
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
