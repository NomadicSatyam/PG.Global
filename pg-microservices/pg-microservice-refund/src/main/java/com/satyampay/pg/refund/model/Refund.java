package com.satyampay.pg.refund.model;


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
public class Refund {

    @Id
    private String id;

    private String transactionId;
    private String merchantCode;
    private Double refundAmount;
    private String status; // PENDING, REFUNDED, FAILED

    private String refundReference;
    private String reason;

    private LocalDateTime requestedAt;
    private LocalDateTime processedAt;
}