package com.miguel.jobnest.infrastructure.consumers;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.EmailService;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.events.SubscriptionCreatedEvent;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionCreatedConsumer {
    private final UserRepository userRepository;
    private final JobVacancyRepository jobVacancyRepository;
    private final EmailService emailService;

    private static final String SUBSCRIPTION_CREATED_QUEUE = "subscription.created.queue";

    @RabbitListener(queues = SUBSCRIPTION_CREATED_QUEUE)
    public void onMessage(SubscriptionCreatedEvent event) {
        final User user = this.getUserById(event.userId());
        final JobVacancy jobVacancy = this.getJobVacancyById(event.jobVacancyId());

        final String text = "Hello, You applied for the job vacancy " + jobVacancy.getTitle() + " at " + jobVacancy.getCompanyName() + ". Please stay attentive to your communication channels, as the company may contact you soon regarding the next steps.";
        final String subject = "Job Vacancy Applied";

        this.emailService.sendEmail(user.getEmail(), text, subject);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private JobVacancy getJobVacancyById(String id) {
        return this.jobVacancyRepository.findById(id).orElseThrow(() -> NotFoundException.with("Job vacancy not found"));
    }
}
