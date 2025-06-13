package com.satyampay.pg.refund.dto;

import lombok.Data;

@Data
public class Transaction {

    private String transactionId;
    private String merchantCode;
    private Double amount;
    private String status;
}
