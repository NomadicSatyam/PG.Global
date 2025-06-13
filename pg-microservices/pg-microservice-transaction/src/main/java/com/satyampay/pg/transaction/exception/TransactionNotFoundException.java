package com.satyampay.pg.transaction.exception;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
