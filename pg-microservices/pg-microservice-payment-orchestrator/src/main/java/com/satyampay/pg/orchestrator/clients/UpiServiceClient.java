package com.satyampay.pg.orchestrator.clients;

import com.satyampay.pg.orchestrator.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UPI-PAYMENT-SERVICE", url = "http://localhost:8085")
public interface UpiServiceClient {


    /**
     * Initiates a UPI payment for the given transaction ID.
     *
     * @param transactionId the ID of the transaction
     * @param dto           the payment request details
     * @return a response entity containing the payment initiation status
     */
    @PostMapping("/api/payments/{transactionId}")
    public ResponseEntity<String> initiateUpiPayment(
            @PathVariable String transactionId,
            @RequestBody PaymentRequest dto);
}
