package com.miguel.jobnest.application.abstractions.wrapper;

public interface TransactionManager {
    void runTransaction(Runnable runnable);
    void afterCommit(Runnable runnable);
}
