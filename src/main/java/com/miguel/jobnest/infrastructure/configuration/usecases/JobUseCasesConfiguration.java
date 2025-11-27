package com.miguel.jobnest.infrastructure.configuration.usecases;

import com.miguel.jobnest.application.abstractions.repositories.JobRepository;
import com.miguel.jobnest.application.abstractions.usecases.job.CreateJobUseCase;
import com.miguel.jobnest.application.abstractions.usecases.job.ListJobsByUserIdUseCase;
import com.miguel.jobnest.application.abstractions.usecases.job.ListJobsUseCase;
import com.miguel.jobnest.application.usecases.job.DefaultCreateJobUseCase;
import com.miguel.jobnest.application.usecases.job.DefaultListJobsByUserIdUseCase;
import com.miguel.jobnest.application.usecases.job.DefaultListJobsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobUseCasesConfiguration {
    @Bean
    public CreateJobUseCase createJobUseCase(JobRepository jobRepository) {
        return new DefaultCreateJobUseCase(jobRepository);
    }

    @Bean
    public ListJobsByUserIdUseCase listJobsByUserIdUseCase(JobRepository jobRepository) {
        return new DefaultListJobsByUserIdUseCase(jobRepository);
    }

    @Bean
    public ListJobsUseCase listJobsUseCase(JobRepository jobRepository) {
        return new DefaultListJobsUseCase(jobRepository);
    }
}
