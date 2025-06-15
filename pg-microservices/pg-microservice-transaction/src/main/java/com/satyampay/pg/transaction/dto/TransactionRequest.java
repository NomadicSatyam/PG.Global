package com.satyampay.pg.transaction.dto;

import lombok.Data;

@Data
public class TransactionRequest {

    private String merchantTransactionId;
    private String transactionId;
    private String merchantCode;
    private Double amount;
    private String currency;
    private String paymentMode;
}
