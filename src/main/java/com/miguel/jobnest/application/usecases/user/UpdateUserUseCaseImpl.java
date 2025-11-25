package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.CacheService;
import com.miguel.jobnest.application.abstractions.usecases.user.UpdateUserUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserInformationUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final CacheService cacheService;

    public UpdateUserUseCaseImpl(
            UserRepository userRepository,
            CacheService cacheService
    ) {
        this.userRepository = userRepository;
        this.cacheService = cacheService;
    }

    @Override
    public void execute(UpdateUserInformationUseCaseInput input) {
        final User user = this.getUserById(input.userId());

        final User updatedUser = user.withName(input.name()).withEmail(input.email()).withDescription(input.description())
                .withCity(input.city()).withState(input.state()).withCountry(input.country());

        final User savedUser = this.saveUser(updatedUser);

        this.cacheService.evict(savedUser.getId());
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private User saveUser(User user) {
        return this.userRepository.save(user);
    }
}
