package com.miguel.jobnest.infrastructure.wrapper;

import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class TransactionExecutorImpl implements TransactionExecutor {
    private final PlatformTransactionManager platformTransactionManager;

    @Override
    public void runTransaction(Runnable runnable) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(this.platformTransactionManager);

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        transactionTemplate.setTimeout(5);

        transactionTemplate.executeWithoutResult(transactionStatus -> runnable.run());
    }

    @Override
    public void registerAfterCommit(Runnable runnable) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                runnable.run();
            }
        });
    }
}
