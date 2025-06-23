package com.satyampay.pg.settlement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
@Builder
public class Settlement {

    @Id
    private String id;

    private String transactionId;
    private String merchantCode;
    private Double amount;
    private String status; // PENDING, SETTLED, FAILED
    private String bankReferenceId;
    private String currency;
    private String settlementReference;
    private LocalDateTime settledAt;
    private LocalDateTime settlementDate;
    private LocalDateTime createdAt;
}
