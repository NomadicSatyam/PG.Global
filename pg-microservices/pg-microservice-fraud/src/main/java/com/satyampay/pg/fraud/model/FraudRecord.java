package com.satyampay.pg.fraud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class FraudRecord {

    @Id
    private String id;

    private String transactionId;
    private String merchantCode;
    private Double amount;
    private String reason;
    private LocalDateTime flaggedAt;
}
