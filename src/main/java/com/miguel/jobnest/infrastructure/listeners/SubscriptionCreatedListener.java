package com.miguel.jobnest.infrastructure.listeners;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.events.SubscriptionCreatedEvent;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.infrastructure.abstractions.services.EmailService;
import com.miguel.jobnest.infrastructure.configurations.json.Json;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaProcessedEventEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionCreatedListener {
    private final UserRepository userRepository;
    private final JobVacancyRepository jobVacancyRepository;
    private final JpaProcessedEventRepository processedEventRepository;
    private final EmailService emailService;

    private static final String SUBSCRIPTION_CREATED_QUEUE = "subscription.created.queue";

    private static final Logger log = LoggerFactory.getLogger(SubscriptionCreatedListener.class);

    @RabbitListener(queues = SUBSCRIPTION_CREATED_QUEUE)
    public void onMessage(@Payload final Message message) {
        final SubscriptionCreatedEvent event = Json.readValue(message.getBody(), SubscriptionCreatedEvent.class);

        final String eventId = event.eventId();
        final String listenerName = SubscriptionCreatedListener.class.getSimpleName();

        if (this.processedEventRepository.existsByEventIdAndListener(eventId, listenerName)) {
            log.info("Event with id: {} has already been processed by the listener: {}", eventId, listenerName);
            return;
        }

        final JobVacancy jobVacancy = this.getJobVacancyById(event.jobVacancyId());

        final String subject = "Subscribed in Job Vacancy";
        final String text = "Hello, You subscribed for the job vacancy " + jobVacancy.getTitle() + " at " + jobVacancy.getCompanyName() + ". Please stay attentive to your communication channels, as the company may contact you soon regarding the next steps.";

        final User user = this.getUserById(event.userId());

        this.emailService.sendEmail(user.getEmail(), text, subject);

        this.processedEventRepository.save(JpaProcessedEventEntity.newProcessedEventEntity(eventId, listenerName));

        log.info("Event with id: {} has been successfully processed by the listener: {}", eventId, listenerName);
    }

    private JobVacancy getJobVacancyById(final String id) {
        return this.jobVacancyRepository.findById(id).orElseThrow(() -> NotFoundException.with("Job vacancy not found"));
    }

    private User getUserById(final String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
