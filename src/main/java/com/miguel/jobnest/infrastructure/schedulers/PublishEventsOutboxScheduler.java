package com.miguel.jobnest.infrastructure.schedulers;

import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.infrastructure.abstractions.producer.MessageProducer;
import com.miguel.jobnest.infrastructure.abstractions.repositories.EventOutboxRepository;
import com.miguel.jobnest.infrastructure.enums.EventOutboxStatus;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublishEventsOutboxScheduler {
    private final EventOutboxRepository eventOutboxRepository;
    private final MessageProducer messageProducer;
    private final TransactionExecutor transactionExecutor;

    public PublishEventsOutboxScheduler(
            final EventOutboxRepository eventOutboxRepository,
            final MessageProducer messageProducer,
            final TransactionExecutor transactionExecutor
    ) {
        this.eventOutboxRepository = eventOutboxRepository;
        this.messageProducer = messageProducer;
        this.transactionExecutor = transactionExecutor;
    }

    private static final Logger log = LoggerFactory.getLogger(PublishEventsOutboxScheduler.class);

    @Scheduled(fixedRate = 10000L) // 10 seconds
    public void publishEvent() {
        log.info("Starting publish events outbox scheduler");

        this.transactionExecutor.runTransaction(() -> {
            final List<JpaEventOutboxEntity> pendingJpaEventsOutbox = this.eventOutboxRepository.findFirst10ByStatus(EventOutboxStatus.PENDING.name());

            log.info("Found {} unpublished events", pendingJpaEventsOutbox.size());

            for (JpaEventOutboxEntity pendingJpaEventOutbox : pendingJpaEventsOutbox) {
                this.messageProducer.publish(pendingJpaEventOutbox);
                this.eventOutboxRepository.save(pendingJpaEventOutbox.withEventOutboxStatus(EventOutboxStatus.PUBLISHED));
                log.info("Event with id: {} has been successfully published", pendingJpaEventOutbox.getEventId());
            }
        });

        log.info("Finished publish events outbox scheduler");
    }
}
