package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.JobRepository;
import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.PaginationMetadata;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaJobEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaJobRepository;
import com.miguel.jobnest.infrastructure.persistence.jpa.specifications.JpaJobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobRepositoryImpl implements JobRepository {
    private final JpaJobRepository jpaJobRepository;

    @Override
    public Job save(Job job) {
        return this.jpaJobRepository.save(JpaJobEntity.from(job)).toEntity();
    }

    @Override
    public Pagination<Job> findAllPaginatedByUserId(String userId, SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaJobEntity> specification = Specification.unrestricted();

        specification = specification.and(JpaJobSpecification.filterByUserId(userId));

        if (!searchQuery.terms().isBlank()) {
            specification = specification.and(JpaJobSpecification.filterByTerms(searchQuery.terms()));
        }

        final Page<JpaJobEntity> pageResult = this.jpaJobRepository.findAll(specification, pageable);

        final List<Job> jobs = pageResult.getContent().stream().map(JpaJobEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(paginationMetadata, jobs);
    }

    @Override
    public Pagination<Job> findAllPaginated(SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaJobEntity> specification = Specification.unrestricted();

        if (!searchQuery.terms().isBlank()) {
            specification = specification.and(JpaJobSpecification.filterByTerms(searchQuery.terms()));
        }

        final Page<JpaJobEntity> pageResult = this.jpaJobRepository.findAll(specification, pageable);

        final List<Job> jobs = pageResult.getContent().stream().map(JpaJobEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(paginationMetadata, jobs);
    }
}
