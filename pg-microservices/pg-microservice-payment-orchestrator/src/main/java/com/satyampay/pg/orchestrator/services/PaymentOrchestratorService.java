package com.satyampay.pg.orchestrator.services;

import com.satyampay.pg.orchestrator.dto.Callback;
import com.satyampay.pg.orchestrator.dto.PaymentRequest;
import com.satyampay.pg.orchestrator.dto.PaymentResponse;

public interface PaymentOrchestratorService {
    void initiatePayment(PaymentRequest dto);
    void handleCallback(Callback dto);
}
