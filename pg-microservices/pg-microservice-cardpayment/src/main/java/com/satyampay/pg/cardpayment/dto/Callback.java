package com.satyampay.pg.cardpayment.dto;

import lombok.Data;

@Data
public class Callback {

    private String merchantTransactionId;
    private String transactionId;
    private String status; // SUCCESS or FAILED
    private String paymentReference;
    private String failureReason;
}
