package com.miguel.jobnest.application.abstractions.transaction;

public interface TransactionExecutor {
    void runTransaction(Runnable runnable);
    void registerAfterCommit(Runnable runnable);
}
