package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.PaginationMetadata;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaJobVacancyEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaJobVacancyRepository;
import com.miguel.jobnest.infrastructure.persistence.jpa.specifications.JpaJobVacancySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JobVacancyRepositoryImpl implements JobVacancyRepository {
    private final JpaJobVacancyRepository jpaJobVacancyRepository;

    @Override
    public JobVacancy save(final JobVacancy jobVacancy) {
        return this.jpaJobVacancyRepository.save(JpaJobVacancyEntity.toEntity(jobVacancy)).toDomain();
    }

    @Override
    public Pagination<JobVacancy> findAllPaginatedByUserId(final String userId, final SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaJobVacancyEntity> specification = Specification.unrestricted();

        specification = specification.and(JpaJobVacancySpecification.filterByUserId(userId));

        if (!searchQuery.terms().isBlank()) {
            specification = specification.and(JpaJobVacancySpecification.filterByTerms(searchQuery.terms()));
        }

        final String isDeleted = searchQuery.filters().get("isDeleted");

        if (isDeleted != null && !isDeleted.isBlank()) {
            specification = specification.and(JpaJobVacancySpecification.filterByIsDeleted(Boolean.parseBoolean(isDeleted)));
        }

        final Page<JpaJobVacancyEntity> pageResult = this.jpaJobVacancyRepository.findAll(specification, pageable);

        final List<JobVacancy> jobVacancies = pageResult.getContent().stream().map(JpaJobVacancyEntity::toDomain).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(paginationMetadata, jobVacancies);
    }

    @Override
    public Pagination<JobVacancy> findAllPaginated(final SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaJobVacancyEntity> specification = Specification.unrestricted();

        specification = specification.and(JpaJobVacancySpecification.filterByIsDeleted(false));

        if (!searchQuery.terms().isBlank()) {
            specification = specification.and(JpaJobVacancySpecification.filterByTerms(searchQuery.terms()));
        }

        final Page<JpaJobVacancyEntity> pageResult = this.jpaJobVacancyRepository.findAll(specification, pageable);

        final List<JobVacancy> jobVacancies = pageResult.getContent().stream().map(JpaJobVacancyEntity::toDomain).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(paginationMetadata, jobVacancies);
    }

    @Override
    public Optional<JobVacancy> findById(final String id) {
        return this.jpaJobVacancyRepository.findById(id).map(JpaJobVacancyEntity::toDomain);
    }
}
