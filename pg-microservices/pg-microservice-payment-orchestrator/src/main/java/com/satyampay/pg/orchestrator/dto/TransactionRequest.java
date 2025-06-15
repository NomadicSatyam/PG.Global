package com.satyampay.pg.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionRequest {

    private String merchantTransactionId; // Unique ID for the transaction from the merchant
    private String transactionId;
    private String merchantCode;
    private Double amount;
    private String currency;
    private String paymentMode;
}
