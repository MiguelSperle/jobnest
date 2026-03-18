package com.miguel.jobnest.infrastructure.abstractions.rest.controllers;

import com.miguel.jobnest.infrastructure.rest.dtos.subscription.res.GetSubscriptionByIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/subscriptions")
public interface SubscriptionControllerAPI {
    @GetMapping("/{subscriptionId}")
    ResponseEntity<GetSubscriptionByIdResponse> getSubscriptionById(@PathVariable String subscriptionId);
}
