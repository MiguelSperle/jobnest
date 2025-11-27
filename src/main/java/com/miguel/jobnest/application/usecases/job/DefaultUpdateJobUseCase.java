package com.miguel.jobnest.application.usecases.job;

import com.miguel.jobnest.application.abstractions.repositories.JobRepository;
import com.miguel.jobnest.application.abstractions.usecases.job.UpdateJobUseCase;
import com.miguel.jobnest.application.usecases.job.inputs.UpdateJobUseCaseInput;
import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultUpdateJobUseCase implements UpdateJobUseCase {
    private final JobRepository jobRepository;

    public DefaultUpdateJobUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void execute(UpdateJobUseCaseInput input) {
        final Job job = this.getJobById(input.id());

        final SeniorityLevel convertedSeniorityLevel = SeniorityLevel.valueOf(input.seniorityLevel());
        final Modality convertedModality = Modality.valueOf(input.modality());

        final Job updatedJob = job.withTitle(input.title()).withDescription(input.description())
                .withSeniorityLevel(convertedSeniorityLevel).withModality(convertedModality);

        this.saveJob(updatedJob);
    }

    private Job getJobById(String id) {
        return this.jobRepository.findById(id).orElseThrow(() -> NotFoundException.with("Job not found"));
    }

    private void saveJob(Job job) {
        this.jobRepository.save(job);
    }
}
