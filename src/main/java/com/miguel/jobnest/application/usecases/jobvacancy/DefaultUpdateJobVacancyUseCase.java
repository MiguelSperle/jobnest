package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.UpdateJobVacancyUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.UpdateJobVacancyUseCaseInput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultUpdateJobVacancyUseCase implements UpdateJobVacancyUseCase {
    private final JobVacancyRepository jobVacancyRepository;

    public DefaultUpdateJobVacancyUseCase(JobVacancyRepository jobVacancyRepository) {
        this.jobVacancyRepository = jobVacancyRepository;
    }

    @Override
    public void execute(final UpdateJobVacancyUseCaseInput input) {
        final JobVacancy jobVacancy = this.getJobVacancyById(input.jobVacancyId());

        final SeniorityLevel convertedSeniorityLevel = SeniorityLevel.valueOf(input.seniorityLevel());
        final Modality convertedModality = Modality.valueOf(input.modality());

        final JobVacancy updatedJobVacancy = jobVacancy.withTitle(input.title()).withDescription(input.description())
                .withSeniorityLevel(convertedSeniorityLevel).withModality(convertedModality).withCompanyName(input.companyName());

        this.saveJobVacancy(updatedJobVacancy);
    }

    private JobVacancy getJobVacancyById(final String id) {
        return this.jobVacancyRepository.findById(id).orElseThrow(() -> NotFoundException.with("Job vacancy not found"));
    }

    private void saveJobVacancy(final JobVacancy jobVacancy) {
        this.jobVacancyRepository.save(jobVacancy);
    }
}
