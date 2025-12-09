package com.miguel.jobnest.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.usecases.user.DefaultGetAuthenticatedUserUseCase;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.utils.UserBuilderTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetAuthenticatedUserUseCaseTest {
    @InjectMocks
    private DefaultGetAuthenticatedUserUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityService securityService;

    @Test
    void shouldGetAuthenticatedUser() {
        final User user = UserBuilderTest.build(UserStatus.VERIFIED, AuthorizationRole.CANDIDATE);

        Mockito.when(this.securityService.getPrincipal()).thenReturn(user.getId());
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        final GetAuthenticatedUserUseCaseOutput output = this.useCase.execute();

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.user());

        Assertions.assertEquals(user.getId(), output.user().getId());
        Assertions.assertEquals(user.getName(), output.user().getName());
        Assertions.assertEquals(user.getEmail(), output.user().getEmail());
        Assertions.assertEquals(user.getDescription(), output.user().getDescription());
        Assertions.assertEquals(user.getUserStatus(), output.user().getUserStatus());
        Assertions.assertEquals(user.getAuthorizationRole(), output.user().getAuthorizationRole());
        Assertions.assertEquals(user.getCity(), output.user().getCity());
        Assertions.assertEquals(user.getState(), output.user().getState());
        Assertions.assertEquals(user.getCountry(), output.user().getCountry());
        Assertions.assertEquals(user.getCreatedAt(), output.user().getCreatedAt());

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist() {
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute()
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
