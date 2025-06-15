package com.satyampay.pg.orchestrator.services;

import com.satyampay.pg.orchestrator.clients.*;
import com.satyampay.pg.orchestrator.dto.*;
import com.satyampay.pg.orchestrator.exception.MerchantAuthException;
import com.satyampay.pg.orchestrator.exception.TransactionNotFoundException;
import com.satyampay.pg.orchestrator.kafka.KafkaProducerService;
import com.satyampay.pg.orchestrator.kafka.KafkaTopicsConstants;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentOrchestratorServiceImpl implements PaymentOrchestratorService {

    private final MerchantServiceClient merchantClient;
    private final CardServiceClient cardClient;
    private final UpiServiceClient upiClient;
    private final NetBankingServiceClient netBankingClient;
    private final TransactionServiceClient transactionClient;
    private final KafkaProducerService kafkaProducerService;


    /**
     * Initiates a payment by:
     * - Validating the merchant API key and KYC status.
     * - Generating a unique transaction ID.
     * - Delegating payment processing to the appropriate mode-specific service.
     *
     * @param dto PaymentRequest containing merchant, amount, and mode-specific info.
     * @return PaymentResponse with transaction ID and initial status.
     */
    @Override
    public PaymentResponse initiatePayment(PaymentRequest dto) {
        // 1. Validate merchant credentials and KYC
        MerchantResponse merchant = merchantClient.getMerchantByCode(dto.getMerchantCode());
        if (!merchant.getApiKey().equals(dto.getApiKey()) || !merchant.isKycVerified()) {
            throw new MerchantAuthException("Invalid API key or unverified merchant.");
        }

        // 2. Generate unique transaction ID and store initial transaction
        String transactionId = UUID.randomUUID().toString();

        // Create initial transaction record
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .transactionId(transactionId)
                .merchantCode(dto.getMerchantCode())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .paymentMode(dto.getPaymentMode())
                .build();

        try {
            transactionClient.createTransaction(transactionRequest);
            log.info("Transaction created with ID: {}", transactionId);
        } catch (FeignException ex) {
            log.error("Failed to create transaction: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create transaction");
        }

        // 3. Route payment request to respective payment mode microservice
        switch (dto.getPaymentMode().toUpperCase()) {
            case "CARD" -> cardClient.initiateCardPayment(transactionId, dto);
            case "UPI" -> upiClient.initiateUpiPayment(transactionId, dto);
            case "NETBANKING" -> netBankingClient.initiateNetBanking(transactionId, dto);
            default -> throw new IllegalArgumentException("Unsupported payment mode.");
        }

        // 4. Respond to merchant with PENDING status
        return PaymentResponse.builder()
                .transactionId(transactionId)
                .status("PENDING")
                .message("Payment request initiated.")
                .build();
    }

    /**
     * Handles callbacks from payment providers.
     * - Updates transaction status in the transaction service.
     * - Handles invalid transaction ID gracefully.
     * - (TODO) Triggers downstream notification or webhook events.
     *
     * @param dto Callback containing transaction ID, status, and reference info.
     */
    @Override
    public void handleCallback(Callback dto) {
        log.info("Received callback: {}", dto);

        // 1. Basic validation for transactionId
        if (dto.getTransactionId() == null || dto.getTransactionId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing transaction ID");
        }

        // 2. Build status update payload
        TransactionStatusUpdate updateDto = TransactionStatusUpdate.builder()
                .status(dto.getStatus())
                .paymentReference(dto.getPaymentReference())
                .failureReason(dto.getFailureReason())
                .build();

        try {
            // 3. Call transaction service to update transaction status
            transactionClient.updateTransactionStatus(dto.getTransactionId(), updateDto);
            log.info("Transaction [{}] updated to [{}]", dto.getTransactionId(), dto.getStatus());

            // 4. TODO: Notify merchant, send webhook, or push Kafka event
            String eventMessage = String.format("Transaction %s status updated to %s", dto.getTransactionId(), dto.getStatus());

            kafkaProducerService.sendTransactionStatus(KafkaTopicsConstants.TRANSACTION_STATUS, updateDto);

        } catch (FeignException.NotFound ex) {
            // Handle missing transaction
            log.error("Transaction not found: {}", dto.getTransactionId());
            throw new TransactionNotFoundException("Transaction not found: " + dto.getTransactionId());
        } catch (FeignException ex) {
            // Catch other errors like 500, timeout, etc.
            log.error("Error updating transaction [{}]: {}", dto.getTransactionId(), ex.getMessage());
            kafkaProducerService.sendTransactionStatus(KafkaTopicsConstants.TRANSACTION_STATUS, updateDto);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to update transaction");
        }
    }
}
