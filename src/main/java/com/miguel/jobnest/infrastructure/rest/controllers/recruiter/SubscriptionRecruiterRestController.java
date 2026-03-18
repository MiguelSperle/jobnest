package com.miguel.jobnest.infrastructure.rest.controllers.recruiter;

import com.miguel.jobnest.application.abstractions.usecases.subscription.ListSubscriptionsByJobVacancyIdUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.ListSubscriptionsByJobVacancyIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByJobVacancyIdUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.abstractions.rest.controllers.recruiter.SubscriptionRecruiterControllerAPI;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.res.ListSubscriptionsByJobVacancyIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionRecruiterRestController implements SubscriptionRecruiterControllerAPI {
    private final ListSubscriptionsByJobVacancyIdUseCase listSubscriptionsByJobVacancyIdUseCase;

    public SubscriptionRecruiterRestController(final ListSubscriptionsByJobVacancyIdUseCase listSubscriptionsByJobVacancyIdUseCase) {
        this.listSubscriptionsByJobVacancyIdUseCase = listSubscriptionsByJobVacancyIdUseCase;
    }

    @Override
    public ResponseEntity<Pagination<ListSubscriptionsByJobVacancyIdResponse>> listSubscriptionsByJobVacancyId(
            final String jobVacancyId,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final ListSubscriptionsByJobVacancyIdUseCaseOutput output = this.listSubscriptionsByJobVacancyIdUseCase.execute(
                ListSubscriptionsByJobVacancyIdUseCaseInput.with(jobVacancyId, SearchQuery.newSearchQuery(page, perPage, sort, direction))
        );

        return ResponseEntity.ok().body(ListSubscriptionsByJobVacancyIdResponse.from(output));
    }
}
