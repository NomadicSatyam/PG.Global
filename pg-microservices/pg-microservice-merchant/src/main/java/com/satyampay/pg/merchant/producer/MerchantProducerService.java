package com.satyampay.pg.merchant.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyampay.pg.merchant.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Sends a payment request to the specified Kafka topic.
     *
     * @param topic   The Kafka topic to send the message to.
     * @param key     The key for the Kafka message.
     * @param payload The PaymentRequest object to be sent as the message payload.
     */
    public void sendMerchantPayment(String topic, String key, PaymentRequest payload) {
        try {
            String message = mapper.writeValueAsString(payload);
            kafkaTemplate.send(topic, key, message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
