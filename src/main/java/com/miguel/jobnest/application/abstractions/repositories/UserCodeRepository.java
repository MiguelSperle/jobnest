package com.miguel.jobnest.application.abstractions.repositories;

import com.miguel.jobnest.domain.entities.UserCode;

import java.util.List;
import java.util.Optional;

public interface UserCodeRepository {
    List<UserCode> findAll();
    Optional<UserCode> findById(String id);
    UserCode save(UserCode userCode);
    void deleteById(String id);
}
