package com.satyampay.pg.fraud.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FraudCheckResult {

    private boolean fraudDetected;
    private String reason;
}
