package com.miguel.jobnest.infrastructure.persistence.jpa.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaJobVacancyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaJobVacancyRepository extends JpaRepository<JpaJobVacancyEntity, String>, JpaSpecificationExecutor<JpaJobVacancyEntity> {
}
