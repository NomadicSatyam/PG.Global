package com.satyampay.pg.refund.dto;

import lombok.Data;

@Data
public class RefundStatusUpdate {

    private String refundReference;
    private String status;
}
