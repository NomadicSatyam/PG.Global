package com.satyampay.pg.orchestrator.dto;

import lombok.Data;

@Data
public class Callback {

    private String merchantTransactionId;
    private String transactionId;
    private String status;
    private String paymentReference;
    private String failureReason;

}
