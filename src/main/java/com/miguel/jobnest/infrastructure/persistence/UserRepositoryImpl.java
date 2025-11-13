package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaUserEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public List<User> findAll() {
        return this.jpaUserRepository.findAll().stream().map(JpaUserEntity::toEntity).toList();
    }

    @Override
    public Optional<User> findById(String id) {
        return this.jpaUserRepository.findById(id).map(JpaUserEntity::toEntity);
    }

    @Override
    public User save(User user) {
        return this.jpaUserRepository.save(JpaUserEntity.from(user)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.jpaUserRepository.findByEmail(email).map(JpaUserEntity::toEntity);
    }
}
