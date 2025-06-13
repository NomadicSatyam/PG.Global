package com.satyampay.pg.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Transaction {

    @Id
    private String transactionId;

    private String merchantCode;
    private Double amount;
    private String currency;
    private String paymentMode; // CARD, UPI, NETBANKING

    private String status; // PENDING, SUCCESS, FAILED
    private String paymentReference;
    private String failureReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
