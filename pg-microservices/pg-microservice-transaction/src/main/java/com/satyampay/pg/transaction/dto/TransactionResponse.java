package com.satyampay.pg.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {

    private String transactionId;
    private String merchantCode;
    private String status;
    private String paymentMode;
    private Double amount;
    private String currency;
    private String paymentReference;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
