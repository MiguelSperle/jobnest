package com.miguel.jobnest.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.usecases.jobvacancy.DefaultListJobVacanciesByUserIdUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.PaginationMetadata;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.utils.JobVacancyTestBuilder;
import com.miguel.jobnest.utils.UserTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListJobVacanciesByUserIdUseCaseTest {
    @InjectMocks
    private DefaultListJobVacanciesByUserIdUseCase useCase;

    @Mock
    private JobVacancyRepository jobVacancyRepository;

    @Mock
    private SecurityService securityService;

    @Test
    void shouldListJobVacanciesByUserId() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(user.getId()).build();
        final List<JobVacancy> jobVacancies = List.of(jobVacancy);

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(0, 10, "createdAt", "desc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, jobVacancies.size()
        );

        final ListJobVacanciesByUserIdUseCaseInput input = ListJobVacanciesByUserIdUseCaseInput.with(
                searchQuery
        );

        final Pagination<JobVacancy> paginatedJobVacancies = new Pagination<>(
                paginationMetadata, jobVacancies
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(user.getId());
        Mockito.when(this.jobVacancyRepository.findAllPaginatedByUserId(Mockito.any(), Mockito.any())).thenReturn(paginatedJobVacancies);

        final ListJobVacanciesByUserIdUseCaseOutput output = this.useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.paginatedJobVacancies());
        Assertions.assertEquals(paginatedJobVacancies, output.paginatedJobVacancies());
        Assertions.assertEquals(paginatedJobVacancies.paginationMetadata(), output.paginatedJobVacancies().paginationMetadata());
        Assertions.assertEquals(paginatedJobVacancies.items(), output.paginatedJobVacancies().items());
        Assertions.assertEquals(1, output.paginatedJobVacancies().items().size());

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).findAllPaginatedByUserId(Mockito.any(), Mockito.any());
    }
}
