package com.miguel.jobnest.domain.events;

import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.utils.IdentifierUtils;

public record UserCodeCreatedEvent(
        String eventId,
        String code,
        UserCodeType userCodeType,
        String userId
) {
    public static UserCodeCreatedEvent from(
            String code,
            UserCodeType userCodeType,
            String userId
    ) {
        return new UserCodeCreatedEvent(
                IdentifierUtils.generateNewId(),
                code,
                userCodeType,
                userId
        );
    }
}
