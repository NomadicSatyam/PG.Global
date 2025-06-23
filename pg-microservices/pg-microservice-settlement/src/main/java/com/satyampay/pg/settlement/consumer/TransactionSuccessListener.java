package com.satyampay.pg.settlement.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyampay.pg.settlement.config.AppConstants;
import com.satyampay.pg.settlement.dto.TransactionSuccessEvent;
import com.satyampay.pg.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionSuccessListener {

    private final ObjectMapper objectMapper;
    private final SettlementService settlementService;

    @KafkaListener(topics = AppConstants.TRANSACTION_STATUS_SUCCESS, groupId = "group-1")
    public void onSuccessTransaction(ConsumerRecord<String, String> record) {

        try {

            String key = record.key();
            String value = record.value();

            TransactionSuccessEvent event = objectMapper.readValue(value, TransactionSuccessEvent.class);
            settlementService.settle(event);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

