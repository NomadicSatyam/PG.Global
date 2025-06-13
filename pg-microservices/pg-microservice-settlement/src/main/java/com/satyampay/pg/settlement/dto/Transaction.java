package com.satyampay.pg.settlement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {

    private String transactionId;
    private String merchantCode;
    private Double amount;
    private String status; // SUCCESS, etc.
    private LocalDateTime createdAt;
}
