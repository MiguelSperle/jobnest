package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.PaginationMetadata;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaSubscriptionEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaSubscriptionRepository;
import com.miguel.jobnest.infrastructure.persistence.jpa.specifications.JpaSubscriptionSpecification;
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
public class SubscriptionRepositoryImpl implements SubscriptionRepository {
    private final JpaSubscriptionRepository jpaSubscriptionRepository;

    @Override
    public boolean existsByUserIdAndJobVacancyId(String userId, String jobVacancyId) {
        return this.jpaSubscriptionRepository.existsByUserIdAndJobVacancyId(userId, jobVacancyId);
    }

    @Override
    public Subscription save(Subscription subscription) {
        return this.jpaSubscriptionRepository.save(JpaSubscriptionEntity.toEntity(subscription)).toDomain();
    }

    @Override
    public Optional<Subscription> findById(String id) {
        return this.jpaSubscriptionRepository.findById(id).map(JpaSubscriptionEntity::toDomain);
    }

    @Override
    public Pagination<Subscription> findAllPaginatedByUserId(String userId, SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaSubscriptionEntity> specification = Specification.unrestricted();

        specification = specification.and(JpaSubscriptionSpecification.filterByUserId(userId));

        final Page<JpaSubscriptionEntity> pageResult = this.jpaSubscriptionRepository.findAll(specification, pageable);

        final List<Subscription> subscriptions = pageResult.getContent().stream().map(JpaSubscriptionEntity::toDomain).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(paginationMetadata, subscriptions);
    }

    @Override
    public List<Subscription> findAllByJobVacancyId(String jobVacancyId) {
        return this.jpaSubscriptionRepository.findAllByJobVacancyId(jobVacancyId).stream().map(JpaSubscriptionEntity::toDomain).toList();
    }

    @Override
    public Pagination<Subscription> findAllPaginatedByJobVacancyId(String jobVacancyId, SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaSubscriptionEntity> specification = Specification.unrestricted();

        specification = specification.and(JpaSubscriptionSpecification.filterByJobVacancyId(jobVacancyId));
        specification = specification.and(JpaSubscriptionSpecification.filterByIsCanceled(false));

        final Page<JpaSubscriptionEntity> pageResult = this.jpaSubscriptionRepository.findAll(specification, pageable);

        final List<Subscription> subscriptions = pageResult.getContent().stream().map(JpaSubscriptionEntity::toDomain).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(paginationMetadata, subscriptions);
    }
}
