package com.miguel.jobnest.infrastructure.persistence.jpa.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<JpaUserEntity, String> {
}
