package com.satyampay.pg.settlement.service;

import com.satyampay.pg.settlement.client.TransactionServiceClient;
import com.satyampay.pg.settlement.dto.SettlementResponse;
import com.satyampay.pg.settlement.dto.Transaction;
import com.satyampay.pg.settlement.model.Settlement;
import com.satyampay.pg.settlement.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
}

