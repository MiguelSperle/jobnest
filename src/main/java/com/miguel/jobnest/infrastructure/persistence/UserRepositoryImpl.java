package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaUserEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(final JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findById(final String id) {
        return this.jpaUserRepository.findById(id).map(JpaUserEntity::toDomain);
    }

    @Override
    public User save(final User user) {
        this.jpaUserRepository.save(JpaUserEntity.toEntity(user));
        return user;
    }

    @Override
    public boolean existsByEmail(final String email) {
        return this.jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return this.jpaUserRepository.findByEmail(email).map(JpaUserEntity::toDomain);
    }
}
