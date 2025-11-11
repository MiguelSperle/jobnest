package com.miguel.jobnest.application.abstractions.producer;

public interface MessageProducer {
    void publish(String exchange, String routingKey, Object message);
}
