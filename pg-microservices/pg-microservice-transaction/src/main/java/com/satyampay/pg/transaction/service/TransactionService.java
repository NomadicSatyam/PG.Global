package com.satyampay.pg.transaction.service;


import com.satyampay.pg.transaction.dto.TransactionRequest;
import com.satyampay.pg.transaction.dto.TransactionResponse;
import com.satyampay.pg.transaction.dto.TransactionStatusUpdate;

public interface TransactionService {
    void createTransaction(TransactionRequest dto);
    void updateStatus(TransactionStatusUpdate dto);
    TransactionResponse getById(String transactionId);
}
