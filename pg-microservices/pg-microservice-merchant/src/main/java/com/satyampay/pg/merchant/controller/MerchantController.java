package com.satyampay.pg.merchant.controller;

import com.satyampay.pg.merchant.client.TransactionServiceClient;
import com.satyampay.pg.merchant.config.AppConstants;
import com.satyampay.pg.merchant.consumer.TransactionConsumerService;
import com.satyampay.pg.merchant.dto.*;
import com.satyampay.pg.merchant.producer.MerchantProducerService;
import com.satyampay.pg.merchant.services.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    private final MerchantProducerService merchantProducerService;

    private final TransactionConsumerService transactionConsumerService;

    private final TransactionServiceClient transactionClient;

    @PostMapping("/create")
    public ResponseEntity<MerchantResponse> create(@Valid @RequestBody MerchantRequest dto) {
        return ResponseEntity.ok(merchantService.createMerchant(dto));
    }

    @GetMapping("/{code}")
    public ResponseEntity<MerchantResponse> get(@PathVariable String code) {
        return ResponseEntity.ok(merchantService.getMerchantByCode(code));
    }

    @PutMapping("/{code}")
    public ResponseEntity<MerchantResponse> update(
            @PathVariable String code,
            @Valid @RequestBody MerchantRequest dto) {
        return ResponseEntity.ok(merchantService.updateMerchant(code, dto));
    }

    @PatchMapping("/{code}/verify-kyc")
    public ResponseEntity<Void> verifyKyc(@PathVariable String code) {
        merchantService.verifyKyc(code);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> initiateMerchantPayment(@RequestBody @Valid PaymentRequest dto) {

        // generate a unique merchant transaction ID for to check the status of the payment later
        String merchantTransactionId = UUID.randomUUID().toString();

        // Set the generated Merchant Transaction ID in the DTO
        dto.setMerchantTransactionId(merchantTransactionId);


        // Send payment request to Kafka topic
        merchantProducerService.sendMerchantPayment(AppConstants.MERCHANT_PAYMENTS, dto.getMerchantCode(), dto);


        long startTime = System.currentTimeMillis();

        // Exit loop after 2 minutes
        do {
            Map<String, TransactionStatusUpdate> statusMap = transactionConsumerService.getStatusMap();
            // Check if the payment is still processing
            if (statusMap.containsKey(dto.getMerchantTransactionId())) {

                // If the payment is no longer processing, return the response
                TransactionStatusUpdate statusUpdate = statusMap.get(dto.getMerchantTransactionId());

                transactionClient.updateStatus(statusUpdate);

                String message = "Payment Processed";
                if ("FRAUDULENT".equalsIgnoreCase(statusUpdate.getStatus())) {
                    message = "Payment marked as FRAUD";
                }

                PaymentResponse response = PaymentResponse.builder()
                        .merchantTransactionId(dto.getMerchantTransactionId())
                        .transactionId(statusUpdate.getTransactionId())
                        .message(message)
                        .status(statusUpdate.getStatus())
                        .build();

                return ResponseEntity.ok(response); // Exit loop when payment is no longer processing
            }
            try {
                Thread.sleep(1000); // Wait for 1 second before checking again
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                throw new RuntimeException("Payment processing interrupted", e);
            }
        } while (System.currentTimeMillis() - startTime <= 2 * 60 * 1000);

        PaymentResponse response = PaymentResponse.builder()
                                                  .message("Payment Processing failed")
                                                  .merchantTransactionId(dto.getMerchantTransactionId())
                                                  .build();
        return ResponseEntity.ok(response);
    }
}

