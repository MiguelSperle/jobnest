package com.miguel.jobnest.infrastructure.rest.controllers.candidate;

import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.ListJobVacanciesUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.abstractions.rest.controllers.candidate.JobVacancyCandidateControllerAPI;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res.ListJobVacanciesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobVacancyCandidateRestController implements JobVacancyCandidateControllerAPI {
    private final ListJobVacanciesUseCase listJobVacanciesUseCase;

    public JobVacancyCandidateRestController(final ListJobVacanciesUseCase listJobVacanciesUseCase) {
        this.listJobVacanciesUseCase = listJobVacanciesUseCase;
    }

    @Override
    public ResponseEntity<Pagination<ListJobVacanciesResponse>> listJobVacancies(
            final int page,
            final int perPage,
            final String search,
            final String sort,
            final String direction
    ) {
        final ListJobVacanciesUseCaseOutput output = this.listJobVacanciesUseCase.execute(ListJobVacanciesUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, search, sort, direction)
        ));

        return ResponseEntity.ok().body(ListJobVacanciesResponse.from(output));
    }
}
