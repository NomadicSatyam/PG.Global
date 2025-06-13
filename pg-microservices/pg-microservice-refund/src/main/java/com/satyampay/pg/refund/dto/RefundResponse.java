package com.satyampay.pg.refund.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class RefundResponse {

    private String id;
    private String transactionId;
    private String merchantCode;
    private Double refundAmount;
    private String status;
    private String refundReference;
    private String reason;
    private LocalDateTime requestedAt;
    private LocalDateTime processedAt;
}
