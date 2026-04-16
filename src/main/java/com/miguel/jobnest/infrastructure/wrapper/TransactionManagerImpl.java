package com.miguel.jobnest.infrastructure.wrapper;

import com.miguel.jobnest.application.abstractions.wrapper.TransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class TransactionManagerImpl implements TransactionManager {
    private final PlatformTransactionManager platformTransactionManager;

    public TransactionManagerImpl(final PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public void runTransaction(final Runnable runnable) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(this.platformTransactionManager);

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);

        transactionTemplate.executeWithoutResult(transactionStatus -> runnable.run());
    }

    @Override
    public void afterCommit(final Runnable runnable) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                runnable.run();
            }
        });
    }
}
