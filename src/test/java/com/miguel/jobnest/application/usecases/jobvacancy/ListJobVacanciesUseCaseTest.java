package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesUseCaseOutput;
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
public class ListJobVacanciesUseCaseTest {
    @InjectMocks
    private DefaultListJobVacanciesUseCase useCase;

    @Mock
    private JobVacancyRepository jobVacancyRepository;

    @Test
    void shouldListJobVacancies() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(user.getId()).build();
        final List<JobVacancy> jobVacancies = List.of(jobVacancy);
        final int page = 0;
        final int perPage = 10;
        final String sort = "createdAt";
        final String direction = "desc";
        final int totalPages = 1;

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(page, perPage, sort, direction);

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), totalPages, jobVacancies.size()
        );

        final ListJobVacanciesUseCaseInput input = ListJobVacanciesUseCaseInput.with(
                searchQuery
        );

        final Pagination<JobVacancy> paginatedJobVacancies = new Pagination<>(
                paginationMetadata, jobVacancies
        );

        Mockito.when(this.jobVacancyRepository.findAllPaginated(Mockito.any())).thenReturn(paginatedJobVacancies);

        final ListJobVacanciesUseCaseOutput output = this.useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.paginatedJobVacancies());
        Assertions.assertEquals(paginatedJobVacancies, output.paginatedJobVacancies());
        Assertions.assertEquals(paginatedJobVacancies.paginationMetadata(), output.paginatedJobVacancies().paginationMetadata());
        Assertions.assertEquals(paginatedJobVacancies.items(), output.paginatedJobVacancies().items());

        Mockito.verify(this.jobVacancyRepository, Mockito.times(1)).findAllPaginated(Mockito.any());
    }
}
