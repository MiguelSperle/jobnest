package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaUserCodeEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaUserCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCodeRepositoryImpl implements UserCodeRepository {
    private final JpaUserCodeRepository jpaUserCodeRepository;

    @Override
    public Optional<UserCode> findById(final String id) {
        return this.jpaUserCodeRepository.findById(id).map(JpaUserCodeEntity::toDomain);
    }

    @Override
    public UserCode save(final UserCode userCode) {
        return this.jpaUserCodeRepository.save(JpaUserCodeEntity.toEntity(userCode)).toDomain();
    }

    @Override
    public void deleteById(final String id) {
        this.jpaUserCodeRepository.deleteById(id);
    }

    @Override
    public Optional<UserCode> findByCodeAndCodeType(final String code, final String codeType) {
        return this.jpaUserCodeRepository.findByCodeAndCodeType(code, codeType).map(JpaUserCodeEntity::toDomain);
    }

    @Override
    public Optional<UserCode> findByUserIdAndCodeType(final String userId, final String codeType) {
        return this.jpaUserCodeRepository.findByUserIdAndCodeType(userId, codeType).map(JpaUserCodeEntity::toDomain);
    }
}
