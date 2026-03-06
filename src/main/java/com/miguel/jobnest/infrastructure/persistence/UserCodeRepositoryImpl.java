package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.events.DomainEvent;
import com.miguel.jobnest.infrastructure.abstractions.repositories.EventOutboxRepository;
import com.miguel.jobnest.infrastructure.configurations.json.Json;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaUserCodeEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaUserCodeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserCodeRepositoryImpl implements UserCodeRepository {
    private final JpaUserCodeRepository jpaUserCodeRepository;
    private final EventOutboxRepository eventOutboxRepository;
    private final TransactionExecutor transactionExecutor;

    public UserCodeRepositoryImpl(
            final JpaUserCodeRepository jpaUserCodeRepository,
            final EventOutboxRepository eventOutboxRepository,
            final TransactionExecutor transactionExecutor
    ) {
        this.jpaUserCodeRepository = jpaUserCodeRepository;
        this.eventOutboxRepository = eventOutboxRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public Optional<UserCode> findById(final String id) {
        return this.jpaUserCodeRepository.findById(id).map(JpaUserCodeEntity::toDomain);
    }

    @Override
    public UserCode save(final UserCode userCode) {
        this.transactionExecutor.runTransaction(() -> {
            this.jpaUserCodeRepository.save(JpaUserCodeEntity.toEntity(userCode));
            for (DomainEvent domainEvent : userCode.getDomainEvents()) {
                this.eventOutboxRepository.save(JpaEventOutboxEntity.newJpaEventOutboxEntity(
                        domainEvent.eventId(),
                        Json.writeValueAsBytes(domainEvent),
                        domainEvent.aggregateId(),
                        domainEvent.aggregateType(),
                        domainEvent.eventType()
                ));
            }
        });

        return userCode;
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
