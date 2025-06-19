package com.satyampay.pg.merchant.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyampay.pg.merchant.config.AppConstants;
import com.satyampay.pg.merchant.dto.TransactionStatusUpdate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Getter
public class TransactionConsumerService {

    private final ObjectMapper objectMapper;
    public Map<String, TransactionStatusUpdate> statusMap = new HashMap<>();

    @KafkaListener(topics = AppConstants.TRANSACTION_STATUS, groupId = AppConstants.GROUP_ID)
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String key = record.key();
            String value = record.value();

            System.out.println("Raw Kafka message received: " + value);


            TransactionStatusUpdate status = objectMapper.readValue(value, TransactionStatusUpdate.class);

            System.out.println("🔔 Notification consumer received status update for transaction " + status.getTransactionId());
            System.out.println("   Status: " +  status.getStatus());
            System.out.println("   Payment Reference: " + status.getPaymentReference());

            // Store the status in a map for quick access
            statusMap.put(status.getMerchantTransactionId(), status);

            sendNotifications(status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotifications(TransactionStatusUpdate status) {
        System.out.println("📱 Sending push notifications for merchant: "+
                "\nTransaction ID: " + status.getTransactionId() +
                "\nStatus: " + status.getStatus() +
                "\nPayment Reference: " + status.getPaymentReference() +
                "\nFailure Reason: " + status.getFailureReason());
    }
}
