package com.miguel.jobnest.application.usecases.job;

import com.miguel.jobnest.application.abstractions.repositories.JobRepository;
import com.miguel.jobnest.application.abstractions.usecases.job.CreateJobUseCase;
import com.miguel.jobnest.application.usecases.job.inputs.CreateJobUseCaseInput;
import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;

public class CreateJobUseCaseImpl implements CreateJobUseCase {
    private final JobRepository jobRepository;

    public CreateJobUseCaseImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void execute(CreateJobUseCaseInput input) {
        final SeniorityLevel seniorityLevel = SeniorityLevel.valueOf(input.seniorityLevel());
        final Modality modality = Modality.valueOf(input.modality());

        final Job newJob = Job.newJob(input.userId(), input.title(), input.description(), seniorityLevel, modality);

        this.saveJob(newJob);
    }

    private void saveJob(Job job) {
        this.jobRepository.save(job);
    }
}
