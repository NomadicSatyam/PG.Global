package com.satyampay.pg.orchestrator.exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String message) {
            super(message);
    }
}
