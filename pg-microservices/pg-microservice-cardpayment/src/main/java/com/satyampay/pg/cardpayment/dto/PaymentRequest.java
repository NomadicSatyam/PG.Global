package com.satyampay.pg.cardpayment.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PaymentRequest {

    private String merchantTransactionId;
    private String merchantCode;
    private Double amount;
    private String currency;
    private String paymentMode;
    private String apiKey;
    private Map<String, String> paymentDetails;
    private String redirectUrl;
}
