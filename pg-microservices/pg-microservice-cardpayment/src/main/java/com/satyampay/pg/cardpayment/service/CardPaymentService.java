package com.satyampay.pg.cardpayment.service;

import com.satyampay.pg.cardpayment.client.OrchestratorServiceClient;
import com.satyampay.pg.cardpayment.dto.Callback;
import com.satyampay.pg.cardpayment.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardPaymentService {

    private final OrchestratorServiceClient orchestratorClient;
    public void processCardPayment(String transactionId, PaymentRequest dto) {
        // Simulate card payment logic
        log.info("Processing card payment for Txn: {}", transactionId);
        log.info("Card Info: {}", dto.getPaymentDetails());

        // Simulate payment logic
        String paymentRef = "CARDREF-" + UUID.randomUUID();
        String status = "SUCCESS"; // Or "FAILED"
        String failureReason = null; // If any

        // Build callback payload
        Callback callback = new Callback();
        callback.setTransactionId(transactionId);
        callback.setStatus(status);
        callback.setPaymentReference(paymentRef);
        callback.setFailureReason(failureReason);


        // In real world: integrate with card gateway like Visa/MC/RuPay

        // Simulate async callback (e.g., send back to orchestrator)
        orchestratorClient.sendCallback(callback);


    }
}
