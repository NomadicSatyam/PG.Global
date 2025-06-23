package com.satyampay.pg.settlement.service;

import com.satyampay.pg.settlement.client.TransactionServiceClient;
import com.satyampay.pg.settlement.dto.SettlementResponse;
import com.satyampay.pg.settlement.dto.Transaction;
import com.satyampay.pg.settlement.dto.TransactionSuccessEvent;
import com.satyampay.pg.settlement.model.Settlement;
import com.satyampay.pg.settlement.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SettlementServiceImpl implements SettlementService {

    private final TransactionServiceClient transactionClient;
    private final SettlementRepository repository;

    @Override
    public void processSettlements() {
        List<Transaction> transactions = transactionClient.getAllTransactions();

        transactions.stream()
                .filter(tx -> "SUCCESS".equalsIgnoreCase(tx.getStatus()))
                .filter(tx -> repository.findByTransactionId(tx.getTransactionId()).isEmpty())
                .forEach(tx -> {
                    Settlement settlement = Settlement.builder()
                            .transactionId(tx.getTransactionId())
                            .merchantCode(tx.getMerchantCode())
                            .amount(tx.getAmount())
                            .status("SETTLED")
                            .bankReferenceId("BANKREF-" + UUID.randomUUID())
                            .settlementDate(LocalDateTime.now())
                            .createdAt(LocalDateTime.now())
                            .build();

                    repository.save(settlement);
                });
    }

    @Override
    public List<SettlementResponse> getSettlementsForMerchant(String merchantCode) {
        return repository.findAll().stream()
                .filter(s -> s.getMerchantCode().equals(merchantCode))
                .map(s -> SettlementResponse.builder()
                        .transactionId(s.getTransactionId())
                        .amount(s.getAmount())
                        .status(s.getStatus())
                        .bankReferenceId(s.getBankReferenceId())
                        .settlementDate(s.getSettlementDate())
                        .build())
                .toList();
    }

    @Override
    public void settle(TransactionSuccessEvent event) {

        log.info("Processing settlement for transaction: {}", event.getTransactionId());

        Settlement settlement = new Settlement();
        settlement.setTransactionId(event.getTransactionId());
        settlement.setMerchantCode(event.getMerchantCode());
        settlement.setAmount(event.getAmount());
        settlement.setCurrency(event.getCurrency());
        settlement.setStatus("SETTLED");
        settlement.setSettlementReference("SETTLE-" + UUID.randomUUID());
        settlement.setSettledAt(LocalDateTime.now());
        repository.save(settlement);
    }

    @Override
    public List<SettlementResponse> getAllSettlements() {
        return repository.findAll().stream()
                .map(s -> SettlementResponse.builder()
                        .transactionId(s.getTransactionId())
                        .amount(s.getAmount())
                        .status(s.getStatus())
                        .bankReferenceId(s.getBankReferenceId())
                        .settlementDate(s.getSettlementDate())
                        .build())
                .toList();
    }
}

