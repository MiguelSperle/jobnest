package com.miguel.jobnest.infrastructure.consumers;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.EmailService;
import com.miguel.jobnest.application.abstractions.services.RedisService;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.events.SubscriptionCreatedEvent;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionCreatedConsumer {
    private final UserRepository userRepository;
    private final JobVacancyRepository jobVacancyRepository;
    private final EmailService emailService;
    private final RedisService redisService;

    private static final String SUBSCRIPTION_CREATED_QUEUE = "subscription.created.queue";

    private static final String SUBSCRIPTION_CREATED_EVENT_KEY_PREFIX = "subscription-created-event:";
    private static final long ttl = 1;
    private static final TimeUnit timeUnit = TimeUnit.HOURS;

    @RabbitListener(queues = SUBSCRIPTION_CREATED_QUEUE)
    public void onMessage(SubscriptionCreatedEvent event) {
        final String eventId = event.eventId();
        final String redisKey = SUBSCRIPTION_CREATED_EVENT_KEY_PREFIX.concat(eventId);
        final String eventName = event.getClass().getSimpleName();

        if (this.redisService.existsByKey(redisKey)) {
            log.info("Event has already been processed | eventName {}, eventId {}, payload {}", eventName, eventId, event);
            return;
        }

        final JobVacancy jobVacancy = this.getJobVacancyById(event.jobVacancyId());

        final String text = "Hello, You subscribed for the job vacancy " + jobVacancy.getTitle() + " at " + jobVacancy.getCompanyName() + ". Please stay attentive to your communication channels, as the company may contact you soon regarding the next steps.";
        final String subject = "Subscribed in Job Vacancy";

        final User user = this.getUserById(event.userId());

        this.emailService.sendEmail(user.getEmail(), text, subject);

        this.redisService.set(redisKey, "processed", ttl, timeUnit);

        log.info("Event has been processed successfully | eventName {}, eventId {}, payload {}", eventName, eventId, event);
    }

    private JobVacancy getJobVacancyById(String id) {
        return this.jobVacancyRepository.findById(id).orElseThrow(() -> NotFoundException.with("Job vacancy not found"));
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
