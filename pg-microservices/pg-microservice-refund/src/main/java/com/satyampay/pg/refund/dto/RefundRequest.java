package com.satyampay.pg.refund.dto;

import lombok.Data;

@Data
public class RefundRequest {

    private String transactionId;
    private Double refundAmount;
    private String reason;
}
