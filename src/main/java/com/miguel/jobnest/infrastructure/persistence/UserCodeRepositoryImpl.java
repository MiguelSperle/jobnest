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
    public Optional<UserCode> findById(String id) {
        return this.jpaUserCodeRepository.findById(id).map(JpaUserCodeEntity::toDomain);
    }

    @Override
    public UserCode save(UserCode userCode) {
        return this.jpaUserCodeRepository.save(JpaUserCodeEntity.toEntity(userCode)).toDomain();
    }

    @Override
    public void deleteById(String id) {
        this.jpaUserCodeRepository.deleteById(id);
    }

    @Override
    public Optional<UserCode> findByCodeAndCodeType(String code, String codeType) {
        return this.jpaUserCodeRepository.findByCodeAndCodeType(code, codeType).map(JpaUserCodeEntity::toDomain);
    }

    @Override
    public Optional<UserCode> findByUserIdAndCodeType(String userId, String codeType) {
        return this.jpaUserCodeRepository.findByUserIdAndCodeType(userId, codeType).map(JpaUserCodeEntity::toDomain);
    }
}
