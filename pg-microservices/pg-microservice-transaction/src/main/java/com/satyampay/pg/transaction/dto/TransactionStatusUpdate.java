package com.satyampay.pg.transaction.dto;

import lombok.Data;

@Data
public class TransactionStatusUpdate {

    private String transactionId;
    private String status;
    private String paymentReference;
    private String failureReason;
}
