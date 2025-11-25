package com.miguel.jobnest.application.abstractions.repositories;

import com.miguel.jobnest.domain.entities.Job;

public interface JobRepository {
    Job save(Job job);
}
