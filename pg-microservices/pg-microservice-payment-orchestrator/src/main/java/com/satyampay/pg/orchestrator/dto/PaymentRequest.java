package com.satyampay.pg.orchestrator.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PaymentRequest {

    private String merchantCode;
    private Double amount;
    private String currency;
    private String paymentMode; // CARD | UPI | NETBANKING
    private String apiKey;      // Used for merchant verification

    private Map<String, String> paymentDetails; // Mode-specific (e.g., card info or upiId)
    private String redirectUrl;
}
