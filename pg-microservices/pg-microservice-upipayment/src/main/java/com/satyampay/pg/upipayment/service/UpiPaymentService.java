package com.satyampay.pg.upipayment.service;

import com.satyampay.pg.upipayment.dto.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpiPaymentService {
    public void processUpiPayment(String transactionId, PaymentRequest dto) {
        log.info("Processing UPI payment for Txn: {}", transactionId);
        log.info("UPI ID: {}", dto.getPaymentDetails().get("upiId"));

        // Simulate UPI collect request or intent
        // Simulate callback status
    }
}
