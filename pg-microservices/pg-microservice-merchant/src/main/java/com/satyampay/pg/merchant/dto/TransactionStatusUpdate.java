package com.satyampay.pg.merchant.dto;

import lombok.Data;

@Data
public class TransactionStatusUpdate {

    private String merchantTransactionId; // Unique ID for the transaction from the merchant
    private String transactionId;
    private String status;
    private String paymentReference;
    private String failureReason;
}
