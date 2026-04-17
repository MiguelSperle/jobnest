package com.miguel.jobnest.infrastructure.rest.controllers.recruiter;

import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.*;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.SoftDeleteJobVacancyUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.abstractions.rest.controllers.recruiter.JobVacancyRecruiterControllerAPI;
import com.miguel.jobnest.infrastructure.idempotency.IdempotencyKey;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.req.CreateJobVacancyRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.req.UpdateJobVacancyRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res.ListJobVacanciesByUserIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class JobVacancyRecruiterRestController implements JobVacancyRecruiterControllerAPI {
    private final CreateJobVacancyUseCase createJobVacancyUseCase;
    private final ListJobVacanciesByUserIdUseCase listJobVacanciesByUserIdUseCase;
    private final UpdateJobVacancyUseCase updateJobVacancyUseCase;
    private final SoftDeleteJobVacancyUseCase softDeleteJobVacancyUseCase;

    public JobVacancyRecruiterRestController(
            final CreateJobVacancyUseCase createJobVacancyUseCase,
            final ListJobVacanciesByUserIdUseCase listJobVacanciesByUserIdUseCase,
            final UpdateJobVacancyUseCase updateJobVacancyUseCase,
            final SoftDeleteJobVacancyUseCase softDeleteJobVacancyUseCase
    ) {
        this.createJobVacancyUseCase = createJobVacancyUseCase;
        this.listJobVacanciesByUserIdUseCase = listJobVacanciesByUserIdUseCase;
        this.updateJobVacancyUseCase = updateJobVacancyUseCase;
        this.softDeleteJobVacancyUseCase = softDeleteJobVacancyUseCase;
    }

    @Override
    @IdempotencyKey
    public ResponseEntity<MessageResponse> createJobVacancy(final CreateJobVacancyRequest request, final String userId) {
        this.createJobVacancyUseCase.execute(request.toInput(userId));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Job vacancy created successfully"));
    }

    @Override
    public ResponseEntity<Pagination<ListJobVacanciesByUserIdResponse>> listJobVacanciesByUserId(
            final int page,
            final int perPage,
            final String search,
            final String sort,
            final String direction,
            final Map<String, String> filters,
            final String userId
    ) {
        final ListJobVacanciesByUserIdUseCaseOutput output = this.listJobVacanciesByUserIdUseCase.execute(
                ListJobVacanciesByUserIdUseCaseInput.with(userId, SearchQuery.newSearchQuery(page, perPage, search, sort, direction, filters))
        );

        return ResponseEntity.ok().body(ListJobVacanciesByUserIdResponse.from(output));
    }

    @Override
    public ResponseEntity<MessageResponse> updateJobVacancy(final String jobVacancyId, final UpdateJobVacancyRequest request) {
        this.updateJobVacancyUseCase.execute(request.toInput(jobVacancyId));

        return ResponseEntity.ok().body(MessageResponse.from("Job vacancy updated successfully"));
    }

    @Override
    public ResponseEntity<MessageResponse> deleteJobVacancy(final String jobVacancyId) {
        this.softDeleteJobVacancyUseCase.execute(SoftDeleteJobVacancyUseCaseInput.with(jobVacancyId));

        return ResponseEntity.ok().body(MessageResponse.from("Job vacancy deleted successfully"));
    }
}
