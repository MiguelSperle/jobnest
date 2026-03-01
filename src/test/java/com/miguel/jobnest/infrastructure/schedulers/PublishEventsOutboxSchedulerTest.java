package com.miguel.jobnest.infrastructure.schedulers;

import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.infrastructure.abstractions.producer.MessageProducer;
import com.miguel.jobnest.infrastructure.builders.JpaEventOutboxEntityTestBuilder;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaEventOutboxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class PublishEventsOutboxSchedulerTest {
    @InjectMocks
    private PublishEventsOutboxScheduler publishEventsOutboxScheduler;

    @Mock
    private JpaEventOutboxRepository eventOutboxRepository;

    @Mock
    private MessageProducer messageProducer;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    void shouldPublishPendingEventsOutbox_whenPublishEventsOutboxSchedulerRuns() {
        final JpaEventOutboxEntity eventOutboxEntity = JpaEventOutboxEntityTestBuilder.aEventOutboxEntity().build();

        Mockito.doAnswer(invocationOnMock -> {
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());
        Mockito.when(this.eventOutboxRepository.findFirst10ByStatus(Mockito.any())).thenReturn(List.of(eventOutboxEntity));
        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any());
        Mockito.when(this.eventOutboxRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.publishEventsOutboxScheduler.publishEvent();

        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.eventOutboxRepository, Mockito.times(1)).findFirst10ByStatus(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any());
        Mockito.verify(this.eventOutboxRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void shouldNotPublishPendingEventsOutbox_whenPublishEventsOutboxSchedulerRuns_becauseNoneWereFound() {
        Mockito.doAnswer(invocationOnMock -> {
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());
        Mockito.when(this.eventOutboxRepository.findFirst10ByStatus(Mockito.any())).thenReturn(List.of());

        this.publishEventsOutboxScheduler.publishEvent();

        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.eventOutboxRepository, Mockito.times(1)).findFirst10ByStatus(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.never()).publish(Mockito.any());
        Mockito.verify(this.eventOutboxRepository, Mockito.never()).save(Mockito.any());
    }
}
