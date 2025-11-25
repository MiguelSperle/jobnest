package com.miguel.jobnest.application.abstractions.repositories;

import com.miguel.jobnest.domain.entities.UserCode;

import java.util.Optional;

public interface UserCodeRepository {
    Optional<UserCode> findById(String id);
    UserCode save(UserCode userCode);
    void deleteById(String id);
    Optional<UserCode> findByCodeAndCodeType(String code, String codeType);
    Optional<UserCode> findByUserIdAndCodeType(String userId, String codeType);
}
