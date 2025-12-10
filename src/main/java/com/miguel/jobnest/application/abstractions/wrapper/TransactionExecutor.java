package com.miguel.jobnest.application.abstractions.wrapper;

public interface TransactionExecutor {
    void runTransaction(Runnable runnable);
    void makeAfterCommit(Runnable runnable);
}
