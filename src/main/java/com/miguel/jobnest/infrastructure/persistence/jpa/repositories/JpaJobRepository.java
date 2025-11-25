package com.miguel.jobnest.infrastructure.persistence.jpa.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaJobRepository extends JpaRepository<JpaJobEntity, String> {
}
