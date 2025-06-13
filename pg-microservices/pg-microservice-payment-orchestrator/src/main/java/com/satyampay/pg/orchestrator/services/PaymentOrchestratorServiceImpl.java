package com.satyampay.pg.orchestrator.services;

import com.satyampay.pg.orchestrator.clients.CardServiceClient;
import com.satyampay.pg.orchestrator.clients.MerchantServiceClient;
import com.satyampay.pg.orchestrator.clients.NetBankingServiceClient;
import com.satyampay.pg.orchestrator.clients.UpiServiceClient;
import com.satyampay.pg.orchestrator.dto.Callback;
import com.satyampay.pg.orchestrator.dto.MerchantResponse;
import com.satyampay.pg.orchestrator.dto.PaymentRequest;
import com.satyampay.pg.orchestrator.dto.PaymentResponse;
import com.satyampay.pg.orchestrator.exception.MerchantAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentOrchestratorServiceImpl implements PaymentOrchestratorService {

    private final MerchantServiceClient merchantClient;
    private final CardServiceClient cardClient;
    private final UpiServiceClient upiClient;
    private final NetBankingServiceClient netBankingClient;

    /**
     * Initiates a payment request by validating the merchant and forwarding the request
     * to the appropriate payment service based on the payment mode.
     *
     * @param dto PaymentRequest containing merchant code, API key, and payment details.
     * @return PaymentResponse with transaction ID and status.
     */
    @Override
    public PaymentResponse initiatePayment(PaymentRequest dto) {
        // 1. Validate Merchant
        MerchantResponse merchant = merchantClient.getMerchantByCode(dto.getMerchantCode());
        if (!merchant.getApiKey().equals(dto.getApiKey()) || !merchant.isKycVerified()) {
            throw new MerchantAuthException("Invalid API key or unverified merchant.");
        }

        // 2. Generate Transaction ID
        String transactionId = UUID.randomUUID().toString();

        // 3. Forward to respective service
        switch (dto.getPaymentMode().toUpperCase()) {
            case "CARD" -> cardClient.initiateCardPayment(transactionId, dto);
            case "UPI" -> upiClient.initiateUpiPayment(transactionId, dto);
            case "NETBANKING" -> netBankingClient.initiateNetBanking(transactionId, dto);
            default -> throw new IllegalArgumentException("Unsupported payment mode.");
        }

        return PaymentResponse.builder()
                .transactionId(transactionId)
                .status("PENDING")
                .message("Payment request initiated.")
                .build();
    }

    /**
     * Handles the callback from payment services to update transaction status.
     *
     * @param dto Callback containing transaction ID, status, and other details.
     */
    @Override
    public void handleCallback(Callback dto) {
        // To be implemented: update transaction status and notify
        System.out.println("Callback received: " + dto);
    }
}
