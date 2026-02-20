package com.miguel.jobnest.infrastructure.schedulers;

import com.miguel.jobnest.infrastructure.abstractions.producer.MessageProducer;
import com.miguel.jobnest.infrastructure.enums.EventOutboxStatus;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaEventOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PublishEventOutboxScheduler {
    private final JpaEventOutboxRepository eventOutboxRepository;
    private final MessageProducer messageProducer;

    private static final Logger log = LoggerFactory.getLogger(PublishEventOutboxScheduler.class);

    @Scheduled(fixedRate = 5000L) // 5 seconds
    public void publishEvent() {
        log.info("Starting publish events outbox scheduler");

        final List<JpaEventOutboxEntity> pendingEventsOutbox = this.eventOutboxRepository.findFirst10ByStatus(EventOutboxStatus.PENDING.name());

        log.info("Found {} unpublished events", pendingEventsOutbox.size());

        for (JpaEventOutboxEntity pendingEventOutbox : pendingEventsOutbox) {
            this.messageProducer.publish(pendingEventOutbox);
            this.eventOutboxRepository.save(pendingEventOutbox.withEventOutboxStatus(EventOutboxStatus.PUBLISHED));
            log.info("Event with id: {} has been successfully published", pendingEventOutbox.getEventId());
        }

        log.info("Finished publish events outbox scheduler");
    }
}
