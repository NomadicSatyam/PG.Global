package com.satyampay.pg.fraud.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {

    private String transactionId;
    private String merchantCode;
    private Double amount;
    private String paymentMode;
    private LocalDateTime createdAt;
}
