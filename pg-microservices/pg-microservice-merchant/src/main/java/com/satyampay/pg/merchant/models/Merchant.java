package com.satyampay.pg.merchant.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Merchant {

    @Id
    private String id;
    private String merchantCode;
    private String name;
    private String email;
    private String phone;
    private String address;
    private boolean kycVerified;

    private String apiKey;  // Optional: used for merchant auth
}