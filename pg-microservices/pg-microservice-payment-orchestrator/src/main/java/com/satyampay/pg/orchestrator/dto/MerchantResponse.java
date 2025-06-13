package com.satyampay.pg.orchestrator.dto;

import lombok.Data;

@Data
public class MerchantResponse {

    private String id;
    private String merchantCode;
    private String name;
    private boolean kycVerified;
    private String apiKey;
}
