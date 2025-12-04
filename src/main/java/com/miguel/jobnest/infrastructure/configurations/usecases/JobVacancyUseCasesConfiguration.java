package com.miguel.jobnest.infrastructure.configurations.usecases;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.*;
import com.miguel.jobnest.application.usecases.jobvacancy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobVacancyUseCasesConfiguration {
    @Bean
    public CreateJobVacancyUseCase createJobVacancyUseCase(
            JobVacancyRepository jobVacancyRepository,
            SecurityService securityService
    ) {
        return new DefaultCreateJobVacancyUseCase(
                jobVacancyRepository,
                securityService
        );
    }

    @Bean
    public ListJobVacanciesByUserIdUseCase listJobVacanciesByUserIdUseCase(
            JobVacancyRepository jobVacancyRepository,
            SecurityService securityService
    ) {
        return new DefaultListJobVacanciesByUserIdUseCase(
                jobVacancyRepository,
                securityService
        );
    }

    @Bean
    public UpdateJobVacancyUseCase updateJobVacancyUseCase(JobVacancyRepository jobVacancyRepository) {
        return new DefaultUpdateJobVacancyUseCase(jobVacancyRepository);
    }

    @Bean
    public ListJobVacanciesUseCase listJobVacanciesUseCase(JobVacancyRepository jobVacancyRepository) {
        return new DefaultListJobVacanciesUseCase(jobVacancyRepository);
    }

    @Bean
    public GetJobVacancyByIdUseCase getJobVacancyByIdUseCase(JobVacancyRepository jobVacancyRepository) {
        return new DefaultGetJobVacancyByIdUseCase(jobVacancyRepository);
    }

    @Bean
    public SoftDeleteJobVacancyUseCase deleteJobVacancyUseCase(
            JobVacancyRepository jobVacancyRepository,
            SubscriptionRepository subscriptionRepository,
            TransactionExecutor transactionExecutor
    ) {
        return new DefaultSoftDeleteJobVacancyUseCase(
                jobVacancyRepository,
                subscriptionRepository,
                transactionExecutor
        );
    }
}
