package com.satyampay.pg.orchestrator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FraudDetectionRequest {

    private String transactionId;
    private String merchantTransactionId;
    private String merchantCode;
    private double amount;
    private String currency;
    private String paymentMode;
    private String ipAddress;
    private String location;
}