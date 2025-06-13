package com.satyampay.pg.orchestrator.clients;

import com.satyampay.pg.orchestrator.dto.MerchantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "merchant-service", url = "http://localhost:8081")
public interface MerchantServiceClient {

    /**
     * Retrieves merchant details by code.
     *
     * @param code the merchant code
     * @return the merchant response containing details
     */
    @GetMapping("/api/merchants/{code}")
    MerchantResponse getMerchantByCode(@PathVariable String code);

}
