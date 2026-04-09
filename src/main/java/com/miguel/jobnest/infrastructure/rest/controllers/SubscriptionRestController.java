package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.subscription.GetSubscriptionByIdUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.GetSubscriptionByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.GetSubscriptionByIdUseCaseOutput;
import com.miguel.jobnest.infrastructure.abstractions.rest.controllers.SubscriptionControllerAPI;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.res.GetSubscriptionByIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionRestController implements SubscriptionControllerAPI {
    private final GetSubscriptionByIdUseCase getSubscriptionByIdUseCase;

    public SubscriptionRestController(final GetSubscriptionByIdUseCase getSubscriptionByIdUseCase) {
        this.getSubscriptionByIdUseCase = getSubscriptionByIdUseCase;
    }

    @Override
    public ResponseEntity<GetSubscriptionByIdResponse> getSubscriptionById(final String subscriptionId) {
        final GetSubscriptionByIdUseCaseOutput output = this.getSubscriptionByIdUseCase.execute(GetSubscriptionByIdUseCaseInput.with(subscriptionId));

        return ResponseEntity.ok().body(GetSubscriptionByIdResponse.from(output));
    }
}
