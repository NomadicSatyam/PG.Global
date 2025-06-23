package com.satyampay.pg.settlement.dto;

import lombok.Data;

@Data
public class TransactionSuccessEvent {

    private String transactionId;
    private String merchantCode;
    private Double amount;
    private String currency;
}
