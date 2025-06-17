package com.satyampay.pg.orchestrator.services;

import com.satyampay.pg.orchestrator.clients.*;
import com.satyampay.pg.orchestrator.dto.*;
import com.satyampay.pg.orchestrator.exception.FraudResponseStatusException;
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
    private final FraudDetectionServiceClient fraudClient;


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
        // Step 1: Validate merchant
        MerchantResponse merchant = merchantClient.getMerchantByCode(dto.getMerchantCode());
        if (!merchant.getApiKey().equals(dto.getApiKey()) || !merchant.isKycVerified()) {
            throw new MerchantAuthException("Invalid API key or unverified merchant.");
        }

        // Step 2: Generate transaction ID
        String transactionId = UUID.randomUUID().toString();

        // Step 3: Call Fraud Service
        FraudDetectionRequest fraudRequest = FraudDetectionRequest.builder()
                .transactionId(transactionId)
                .merchantCode(dto.getMerchantCode())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .paymentMode(dto.getPaymentMode())
                .build();

        try {
            FraudDetectionResponse fraudResponse = fraudClient.checkTransaction(fraudRequest);
            if ("FRAUDULENT".equalsIgnoreCase(fraudResponse.getStatus())) {
                log.warn("Fraud detected for transaction {}: {}", transactionId, fraudResponse.getReason());
                throw new FraudResponseStatusException("Payment blocked due to fraud: " + fraudResponse.getReason());
            }
        } catch (FeignException e) {
            log.error("Fraud service failed: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Fraud check failed");
        }

        // Step 4: Create transaction
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .merchantTransactionId(dto.getMerchantTransactionId())
                .transactionId(transactionId)
                .merchantCode(dto.getMerchantCode())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .paymentMode(dto.getPaymentMode())
                .build();

        transactionClient.createTransaction(transactionRequest);

        // Step 5: Route payment
        switch (dto.getPaymentMode().toUpperCase()) {
            case "CARD" -> cardClient.initiateCardPayment(transactionId, dto);
            case "UPI" -> upiClient.initiateUpiPayment(transactionId, dto);
            case "NETBANKING" -> netBankingClient.initiateNetBanking(transactionId, dto);
            default -> throw new IllegalArgumentException("Unsupported payment mode.");
        }

        // Step 6: Respond
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
                .merchantTransactionId(dto.getMerchantTransactionId())
                .transactionId(dto.getTransactionId())
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
