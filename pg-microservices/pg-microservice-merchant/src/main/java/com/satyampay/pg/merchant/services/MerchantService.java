package com.satyampay.pg.merchant.services;

import com.satyampay.pg.merchant.dto.MerchantRequest;
import com.satyampay.pg.merchant.dto.MerchantResponse;

public interface MerchantService {
    MerchantResponse createMerchant(MerchantRequest dto);
    MerchantResponse getMerchantByCode(String code);
    MerchantResponse updateMerchant(String code, MerchantRequest dto);
    void verifyKyc(String code);
}
