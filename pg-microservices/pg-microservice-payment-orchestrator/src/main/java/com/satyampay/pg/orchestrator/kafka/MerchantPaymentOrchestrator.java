package com.satyampay.pg.orchestrator.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyampay.pg.orchestrator.dto.PaymentRequest;
import com.satyampay.pg.orchestrator.services.PaymentOrchestratorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class MerchantPaymentOrchestrator {

    private final ObjectMapper objectMapper;

    private final PaymentOrchestratorService orchestratorService;

    @KafkaListener(topics = KafkaTopicsConstants.MERCHANT_PAYMENTS, groupId = KafkaTopicsConstants.GROUP_ID)
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String key = record.key();
            String value = record.value();

            System.out.println("Raw Kafka message received: " + value);
            PaymentRequest paymentRequest = objectMapper.readValue(value, PaymentRequest.class);

            orchestratorService.initiatePayment(paymentRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
