package com.satyampay.pg.fraud.service;

import com.satyampay.pg.fraud.dto.FraudCheckResult;
import com.satyampay.pg.fraud.dto.Transaction;
import com.satyampay.pg.fraud.model.FraudRecord;
import com.satyampay.pg.fraud.repository.FraudRecordRepository;
import com.satyampay.pg.fraud.rules.RuleEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FraudDetectionServiceImpl implements FraudDetectionService {

    private final FraudRecordRepository repository;
    private final RuleEngine ruleEngine;

    @Override
    public FraudCheckResult check(Transaction dto) {
        Optional<String> violation = ruleEngine.runAll(dto);

        if (violation.isPresent()) {
            repository.save(FraudRecord.builder()
                    .transactionId(dto.getTransactionId())
                    .merchantCode(dto.getMerchantCode())
                    .amount(dto.getAmount())
                    .reason(violation.get())
                    .flaggedAt(LocalDateTime.now())
                    .build());

            return FraudCheckResult.builder()
                    .fraudDetected(true)
                    .reason(violation.get())
                    .build();
        }

        return FraudCheckResult.builder().fraudDetected(false).build();
    }

    @Override
    public List<FraudRecord> getMerchantFrauds(String merchantCode) {
        return repository.findByMerchantCode(merchantCode);
    }
}
