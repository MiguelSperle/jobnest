package com.miguel.jobnest.infrastructure.configuration.usecases;

import com.miguel.jobnest.application.abstractions.repositories.JobRepository;
import com.miguel.jobnest.application.abstractions.usecases.job.CreateJobUseCase;
import com.miguel.jobnest.application.usecases.job.CreateJobUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobUseCasesConfiguration {
    @Bean
    public CreateJobUseCase createJobUseCase(JobRepository jobRepository) {
        return new CreateJobUseCaseImpl(jobRepository);
    }
}
