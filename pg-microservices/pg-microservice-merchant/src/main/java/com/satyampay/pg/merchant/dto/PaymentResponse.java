package com.satyampay.pg.merchant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentResponse {

    private String merchantTransactionId; // Unique ID for the transaction from the merchant
    private String transactionId;
    private String status; // PENDING, SUCCESS, FAILED
    private String message;
}
