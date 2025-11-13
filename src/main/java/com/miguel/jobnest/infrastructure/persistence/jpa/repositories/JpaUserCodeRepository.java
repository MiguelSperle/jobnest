package com.miguel.jobnest.infrastructure.persistence.jpa.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaUserCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaUserCodeRepository extends JpaRepository<JpaUserCodeEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM user_codes uc WHERE uc.code = :code AND uc.code_type = :codeType")
    Optional<JpaUserCodeEntity> findByCodeAndCodeType(@Param("code") String code, @Param("codeType") String codeType);

    @Query(nativeQuery = true, value = "SELECT * FROM user_codes uc WHERE uc.user_id = :userId AND uc.code_type = :codeType")
    Optional<JpaUserCodeEntity> findByUserIdAndCodeType(@Param("userId") String userId, @Param("codeType") String codeType);
}
