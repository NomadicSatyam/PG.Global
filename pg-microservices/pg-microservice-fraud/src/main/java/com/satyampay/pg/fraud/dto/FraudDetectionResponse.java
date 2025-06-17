package com.satyampay.pg.fraud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FraudDetectionResponse {

    private String status;;
    private String reason;
}
