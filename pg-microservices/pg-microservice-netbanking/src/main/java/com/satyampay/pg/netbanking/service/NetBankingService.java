package com.satyampay.pg.netbanking.service;

import com.satyampay.pg.netbanking.dto.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NetBankingService {
    public void processNetBanking(String transactionId, PaymentRequest dto) {
        log.info("Processing NetBanking for Txn: {}", transactionId);
        log.info("Bank Code: {}", dto.getPaymentDetails().get("bankCode"));

        // Simulate redirect and status polling
    }
}
