package com.miguel.jobnest.domain.events;

import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserCodeCreatedEventTest {
    @Test
    void shouldReturnUserCodeCreatedEvent_whenCallFrom() {
        final String code = "1NC2R2T4";
        final UserCodeType userCodeType = UserCodeType.USER_VERIFICATION;
        final String userId = IdentifierUtils.generateNewId();
        final String aggregateId =  IdentifierUtils.generateNewId();

        final UserCodeCreatedEvent event = new UserCodeCreatedEvent(code, userCodeType, userId, aggregateId);

        Assertions.assertNotNull(event);
        Assertions.assertEquals(code, event.code());
        Assertions.assertEquals(userCodeType, event.userCodeType());
        Assertions.assertEquals(userId, event.userId());
        Assertions.assertEquals(aggregateId, event.aggregateId());
    }
}
