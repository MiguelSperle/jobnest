package com.miguel.jobnest.domain.events;

import com.miguel.jobnest.domain.enums.UserCodeType;

public record UserCodeCreatedEvent(
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
                code,
                userCodeType,
                userId
        );
    }
}
