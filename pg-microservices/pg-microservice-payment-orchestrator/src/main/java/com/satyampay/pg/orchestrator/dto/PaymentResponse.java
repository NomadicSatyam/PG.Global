package com.satyampay.pg.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentResponse {

    private String transactionId;
    private String status; // PENDING, SUCCESS, FAILED
    private String message;
}
