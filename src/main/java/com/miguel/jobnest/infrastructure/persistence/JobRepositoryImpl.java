package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.JobRepository;
import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaJobEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobRepositoryImpl implements JobRepository {
    private final JpaJobRepository jpaJobRepository;

    @Override
    public Job save(Job job) {
        return this.jpaJobRepository.save(JpaJobEntity.from(job)).toEntity();
    }
}
