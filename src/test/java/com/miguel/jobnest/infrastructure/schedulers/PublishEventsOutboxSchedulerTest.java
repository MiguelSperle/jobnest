package com.miguel.jobnest.infrastructure.schedulers;

import com.miguel.jobnest.application.abstractions.wrapper.TransactionManager;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.infrastructure.abstractions.repositories.EventOutboxRepository;
import com.miguel.jobnest.infrastructure.abstractions.services.EventBusService;
import com.miguel.jobnest.infrastructure.enums.EventOutboxStatus;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PublishEventsOutboxSchedulerTest {
    @InjectMocks
    private PublishEventsOutboxScheduler publishEventsOutboxScheduler;

    @Mock
    private EventOutboxRepository eventOutboxRepository;

    @Mock
    private EventBusService eventBusService;

    @Mock
    private TransactionManager transactionManager;

    @Test
    void shouldPublishPendingEventsOutbox_whenPublishEventsOutboxSchedulerRuns() {
        final JpaEventOutboxEntity jpaEventOutboxEntity = JpaEventOutboxEntity.builder().eventId(IdentifierUtils.generateNewId())
                .eventOutboxStatus(EventOutboxStatus.PENDING).build();

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionManager).runTransaction(Mockito.any());
        Mockito.when(this.eventOutboxRepository.findFirst10ByStatus(Mockito.any())).thenReturn(List.of(jpaEventOutboxEntity));
        Mockito.doNothing().when(this.eventBusService).publish(Mockito.any());
        Mockito.doNothing().when(this.eventOutboxRepository).save(Mockito.any());

        this.publishEventsOutboxScheduler.publishEvent();

        Mockito.verify(this.transactionManager, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.eventOutboxRepository, Mockito.times(1)).findFirst10ByStatus(Mockito.any());
        Mockito.verify(this.eventBusService, Mockito.times(1)).publish(Mockito.any());
        Mockito.verify(this.eventOutboxRepository, Mockito.times(1)).save(Mockito.argThat(jpaEventOutboxEntitySaved ->
                jpaEventOutboxEntitySaved.getEventOutboxStatus() == EventOutboxStatus.PUBLISHED
        ));
    }

    @Test
    void shouldNotPublishPendingEventsOutbox_whenPublishEventsOutboxSchedulerRuns_becauseNoneWereFound() {
        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionManager).runTransaction(Mockito.any());
        Mockito.when(this.eventOutboxRepository.findFirst10ByStatus(Mockito.any())).thenReturn(List.of());

        this.publishEventsOutboxScheduler.publishEvent();

        Mockito.verify(this.transactionManager, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.eventOutboxRepository, Mockito.times(1)).findFirst10ByStatus(Mockito.any());
        Mockito.verify(this.eventBusService, Mockito.never()).publish(Mockito.any());
        Mockito.verify(this.eventOutboxRepository, Mockito.never()).save(Mockito.any());
    }
}
