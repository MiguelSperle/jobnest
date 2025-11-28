package com.miguel.jobnest.infrastructure.configurations.usecases;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.CreateJobVacancyUseCase;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.ListJobVacanciesByUserIdUseCase;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.ListJobVacanciesUseCase;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.UpdateJobVacancyUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.DefaultCreateJobVacancyUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.DefaultListJobVacanciesByUserIdUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.DefaultListJobVacanciesUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.DefaultUpdateJobVacancyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobVacancyUseCasesConfiguration {
    @Bean
    public CreateJobVacancyUseCase createJobVacancyUseCase(JobVacancyRepository jobVacancyRepository) {
        return new DefaultCreateJobVacancyUseCase(jobVacancyRepository);
    }

    @Bean
    public ListJobVacanciesByUserIdUseCase listJobVacanciesByUserIdUseCase(JobVacancyRepository jobVacancyRepository) {
        return new DefaultListJobVacanciesByUserIdUseCase(jobVacancyRepository);
    }

    @Bean
    public UpdateJobVacancyUseCase updateJobVacancyUseCase(JobVacancyRepository jobVacancyRepository) {
        return new DefaultUpdateJobVacancyUseCase(jobVacancyRepository);
    }

    @Bean
    public ListJobVacanciesUseCase listJobVacanciesUseCase(JobVacancyRepository jobVacancyRepository) {
        return new DefaultListJobVacanciesUseCase(jobVacancyRepository);
    }
}
