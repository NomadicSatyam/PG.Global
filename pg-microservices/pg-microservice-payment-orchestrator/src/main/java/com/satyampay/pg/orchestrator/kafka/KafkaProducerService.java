package com.satyampay.pg.orchestrator.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyampay.pg.orchestrator.dto.TransactionStatusUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    public void sendTransactionStatus(String topic, TransactionStatusUpdate message) {

        log.info("Kafka message sent to [{}]: {}", topic, message);

        // Serialize the message to JSON and send to Kafka
        try {
            String key = message.getTransactionId();
            String value = mapper.writeValueAsString(message);
            kafkaTemplate.send(KafkaTopicsConstants.TRANSACTION_STATUS, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
