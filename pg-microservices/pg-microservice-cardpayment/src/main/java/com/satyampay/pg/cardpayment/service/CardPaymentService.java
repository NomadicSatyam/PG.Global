package com.satyampay.pg.cardpayment.service;

import com.satyampay.pg.cardpayment.dto.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CardPaymentService {
    public void processCardPayment(String transactionId, PaymentRequest dto) {
        // Simulate card payment logic
        log.info("Processing card payment for Txn: {}", transactionId);
        log.info("Card Info: {}", dto.getPaymentDetails());

        // In real world: integrate with card gateway like Visa/MC/RuPay

        // Simulate async callback (e.g., send back to orchestrator)
    }
}
