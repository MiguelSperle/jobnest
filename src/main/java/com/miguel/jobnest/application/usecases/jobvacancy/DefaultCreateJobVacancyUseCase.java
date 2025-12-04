package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.CreateJobVacancyUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.CreateJobVacancyUseCaseInput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;

public class DefaultCreateJobVacancyUseCase implements CreateJobVacancyUseCase {
    private final JobVacancyRepository jobVacancyRepository;
    private final SecurityService securityService;

    public DefaultCreateJobVacancyUseCase(
            JobVacancyRepository jobVacancyRepository,
            SecurityService securityService
    ) {
        this.jobVacancyRepository = jobVacancyRepository;
        this.securityService = securityService;
    }

    @Override
    public void execute(CreateJobVacancyUseCaseInput input) {
        final String authenticatedUserId = this.securityService.getPrincipal();

        final SeniorityLevel convertedSeniorityLevel = SeniorityLevel.valueOf(input.seniorityLevel());
        final Modality convertedModality = Modality.valueOf(input.modality());

        final JobVacancy newJobVacancy = JobVacancy.newJobVacancy(
                authenticatedUserId,
                input.title(),
                input.description(),
                convertedSeniorityLevel,
                convertedModality,
                input.companyName()
        );

        this.saveJob(newJobVacancy);
    }

    private void saveJob(JobVacancy jobVacancy) {
        this.jobVacancyRepository.save(jobVacancy);
    }
}
