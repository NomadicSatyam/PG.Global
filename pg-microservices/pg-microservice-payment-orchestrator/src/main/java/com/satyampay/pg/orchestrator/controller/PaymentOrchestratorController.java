package com.satyampay.pg.orchestrator.controller;

import com.satyampay.pg.orchestrator.dto.Callback;
import com.satyampay.pg.orchestrator.dto.PaymentRequest;
import com.satyampay.pg.orchestrator.dto.PaymentResponse;
import com.satyampay.pg.orchestrator.services.PaymentOrchestratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentOrchestratorController {

    private final PaymentOrchestratorService orchestratorService;

    /**
     * Initiates a payment process.
     *
     * @param dto the payment request details
     * @return a response entity containing the payment response
     */
    @PostMapping
    public ResponseEntity<PaymentResponse> initiate(@RequestBody @Valid PaymentRequest dto) {
        return ResponseEntity.ok(orchestratorService.initiatePayment(dto));
    }

    /**
     * Handles the callback from the payment gateway.
     *
     * @param dto the callback details
     * @return a response entity indicating success
     */
    @PostMapping("/callback")
    public ResponseEntity<Void> callback(@RequestBody Callback dto) {
        orchestratorService.handleCallback(dto);
        return ResponseEntity.ok().build();
    }
}
