package com.miguel.jobnest.infrastructure.persistence.jpa.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaJobRepository extends JpaRepository<JpaJobEntity, String>, JpaSpecificationExecutor<JpaJobEntity> {
}
