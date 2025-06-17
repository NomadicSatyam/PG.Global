package com.satyampay.pg.fraud.service;


import com.satyampay.pg.fraud.dto.FraudDetectionRequest;
import com.satyampay.pg.fraud.dto.FraudDetectionResponse;
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
    public FraudDetectionResponse check(FraudDetectionRequest dto) {
        Optional<String> violation = ruleEngine.runAll(dto); // apply rules

        if (violation.isPresent()) {
            // Save fraud record
            repository.save(FraudRecord.builder()
                    .transactionId(dto.getTransactionId())
                    .merchantCode(dto.getMerchantCode())
                    .amount(dto.getAmount())
                    .reason(violation.get())
                    .flaggedAt(LocalDateTime.now())
                    .build());

            return FraudDetectionResponse.builder()
                    .status("FRAUDULENT")
                    .reason(violation.get())
                    .build();
        }

        return FraudDetectionResponse.builder()
                .status("CLEAR")
                .reason("No violations detected")
                .build();
    }

    @Override
    public List<FraudRecord> getMerchantFrauds(String merchantCode) {
        return repository.findByMerchantCode(merchantCode);
    }
}
