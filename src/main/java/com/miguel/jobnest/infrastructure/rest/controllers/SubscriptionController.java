package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.subscription.GetSubscriptionByIdUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.GetSubscriptionByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.GetSubscriptionByIdUseCaseOutput;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.res.GetSubscriptionByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final GetSubscriptionByIdUseCase getSubscriptionByIdUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<GetSubscriptionByIdResponse> getSubscriptionById(@PathVariable String id) {
        final GetSubscriptionByIdUseCaseOutput output = this.getSubscriptionByIdUseCase.execute(GetSubscriptionByIdUseCaseInput.with(id));

        return ResponseEntity.ok().body(GetSubscriptionByIdResponse.from(output));
    }
}
