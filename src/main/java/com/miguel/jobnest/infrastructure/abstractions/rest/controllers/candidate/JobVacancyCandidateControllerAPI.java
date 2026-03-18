package com.miguel.jobnest.infrastructure.abstractions.rest.controllers.candidate;

import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res.ListJobVacanciesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/candidate/job-vacancies")
public interface JobVacancyCandidateControllerAPI {
    @GetMapping
    ResponseEntity<Pagination<ListJobVacanciesResponse>> listJobVacancies(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    );
}
