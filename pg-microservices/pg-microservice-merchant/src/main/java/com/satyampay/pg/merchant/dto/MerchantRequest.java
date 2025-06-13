package com.satyampay.pg.merchant.dto;

import lombok.Data;

@Data
public class MerchantRequest {

    private String name;
    private String email;
    private String phone;
    private String address;
}
