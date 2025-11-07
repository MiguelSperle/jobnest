package com.miguel.jobnest.application.abstractions.repositories;

import com.miguel.jobnest.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(String id);
    User save(User user);
    void deleteById(String id);
}
