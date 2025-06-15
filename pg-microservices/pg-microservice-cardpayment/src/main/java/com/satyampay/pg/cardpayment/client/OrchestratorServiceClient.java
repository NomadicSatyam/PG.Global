package com.satyampay.pg.cardpayment.client;

import com.satyampay.pg.cardpayment.dto.Callback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-ORCHESTRATOR-SERVICE", url = "http://localhost:8082")
public interface OrchestratorServiceClient {

    @PostMapping("/api/payments/callback")
    void sendCallback(@RequestBody Callback callback);
}
