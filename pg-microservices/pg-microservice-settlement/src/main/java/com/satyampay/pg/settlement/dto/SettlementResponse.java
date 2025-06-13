package com.satyampay.pg.settlement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class SettlementResponse {

    private String transactionId;
    private Double amount;
    private String status;
    private String bankReferenceId;
    private LocalDateTime settlementDate;
}
