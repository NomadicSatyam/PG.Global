package com.satyampay.pg.transaction.producer;

import com.satyampay.pg.transaction.dto.TransactionSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionStatusProducer {

    private final KafkaTemplate<String, TransactionSuccessEvent> kafkaTemplate;

    public void publishTransactionSuccess(TransactionSuccessEvent event) {
        log.info("Publishing transaction success event: {}", event.getTransactionId());

        // Send the event to the "Transaction.Success" topic
        kafkaTemplate.send("Transaction.Success", event);
    }

}
