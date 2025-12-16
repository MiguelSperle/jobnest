package com.miguel.jobnest.domain.events;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubscriptionCreatedEventTest {
    @Test
    void shouldReturnSubscriptionCreatedEvent_whenCallFrom() {
        final String userId = IdentifierUtils.generateUUID();
        final String jobVacancyId = IdentifierUtils.generateUUID();

        final SubscriptionCreatedEvent event = SubscriptionCreatedEvent.from(userId, jobVacancyId);

        Assertions.assertNotNull(event);
        Assertions.assertEquals(userId, event.userId());
        Assertions.assertEquals(jobVacancyId, event.jobVacancyId());
    }
}
