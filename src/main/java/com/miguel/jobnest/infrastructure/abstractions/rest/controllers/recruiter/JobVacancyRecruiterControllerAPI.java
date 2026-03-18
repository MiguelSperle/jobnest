package com.miguel.jobnest.infrastructure.abstractions.rest.controllers.recruiter;

import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.req.CreateJobVacancyRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.req.UpdateJobVacancyRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res.ListJobVacanciesByUserIdResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/v1/recruiter/job-vacancies")
public interface JobVacancyRecruiterControllerAPI {
    @PostMapping
    ResponseEntity<MessageResponse> createJobVacancy(@RequestBody @Valid CreateJobVacancyRequest request);

    @GetMapping
    ResponseEntity<Pagination<ListJobVacanciesByUserIdResponse>> listJobVacanciesByUserId(
            @PathVariable String userId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction,
            @RequestParam Map<String, String> filters
    );

    @PatchMapping("/{jobVacancyId}")
    ResponseEntity<MessageResponse> updateJobVacancy(
            @PathVariable String jobVacancyId,
            @RequestBody @Valid UpdateJobVacancyRequest request
    );

    @DeleteMapping("/{jobVacancyId}")
    ResponseEntity<MessageResponse> deleteJobVacancy(@PathVariable String jobVacancyId);
}
