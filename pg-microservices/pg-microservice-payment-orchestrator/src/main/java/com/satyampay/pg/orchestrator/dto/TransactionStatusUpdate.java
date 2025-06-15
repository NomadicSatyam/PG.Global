package com.satyampay.pg.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionStatusUpdate {

    private String transactionId;
    private String status;
    private String paymentReference;
    private String failureReason;
}
