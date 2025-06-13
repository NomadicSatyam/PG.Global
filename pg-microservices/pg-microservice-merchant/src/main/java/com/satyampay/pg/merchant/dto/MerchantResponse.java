package com.satyampay.pg.merchant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantResponse {

    private String id;
    private String merchantCode;
    private String name;
    private String email;
    private String phone;
    private String address;
    private boolean kycVerified;
    private String apiKey;
}
