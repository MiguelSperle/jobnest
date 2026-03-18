package com.miguel.jobnest.infrastructure.rest.controllers.candidate;

import com.miguel.jobnest.application.abstractions.usecases.subscription.CreateSubscriptionUseCase;
import com.miguel.jobnest.application.abstractions.usecases.subscription.ListSubscriptionsByUserIdUseCase;
import com.miguel.jobnest.application.abstractions.usecases.subscription.CancelSubscriptionUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.ListSubscriptionsByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.inputs.CancelSubscriptionUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.abstractions.rest.controllers.candidate.SubscriptionCandidateControllerAPI;
import com.miguel.jobnest.infrastructure.idempotency.IdempotencyKey;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.req.CreateSubscriptionRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.res.ListSubscriptionsByUserIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class SubscriptionCandidateRestController implements SubscriptionCandidateControllerAPI {
    private final CreateSubscriptionUseCase createSubscriptionUseCase;
    private final ListSubscriptionsByUserIdUseCase listSubscriptionsByUserIdUseCase;
    private final CancelSubscriptionUseCase cancelSubscriptionUseCase;

    public SubscriptionCandidateRestController(
            final CreateSubscriptionUseCase createSubscriptionUseCase,
            final ListSubscriptionsByUserIdUseCase listSubscriptionsByUserIdUseCase,
            final CancelSubscriptionUseCase cancelSubscriptionUseCase
    ) {
        this.createSubscriptionUseCase = createSubscriptionUseCase;
        this.listSubscriptionsByUserIdUseCase = listSubscriptionsByUserIdUseCase;
        this.cancelSubscriptionUseCase = cancelSubscriptionUseCase;
    }

    @Override
    @IdempotencyKey
    public ResponseEntity<MessageResponse> createSubscription(final CreateSubscriptionRequest request, final MultipartFile resumeFile) throws IOException {
        this.createSubscriptionUseCase.execute(request.toInput(resumeFile.getBytes()));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Subscription created successfully"));
    }

    @Override
    public ResponseEntity<Pagination<ListSubscriptionsByUserIdResponse>> listSubscriptionsByUserId(
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final ListSubscriptionsByUserIdUseCaseOutput output = this.listSubscriptionsByUserIdUseCase.execute(
                ListSubscriptionsByUserIdUseCaseInput.with(SearchQuery.newSearchQuery(page, perPage, sort, direction))
        );

        return ResponseEntity.ok().body(ListSubscriptionsByUserIdResponse.from(output));
    }

    @Override
    public ResponseEntity<MessageResponse> cancelSubscription(final String subscriptionId) {
        this.cancelSubscriptionUseCase.execute(CancelSubscriptionUseCaseInput.with(subscriptionId));

        return ResponseEntity.ok().body(MessageResponse.from("Subscription canceled successfully"));
    }
}
