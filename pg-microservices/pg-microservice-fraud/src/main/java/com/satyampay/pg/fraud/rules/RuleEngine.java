package com.satyampay.pg.fraud.rules;

import com.satyampay.pg.fraud.dto.FraudDetectionRequest;
import com.satyampay.pg.fraud.dto.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RuleEngine {
    private final List<FraudRule> rules;

    public Optional<String> runAll(FraudDetectionRequest dto) {
        for (FraudRule rule : rules) {
            Optional<String> result = rule.evaluate(dto);
            if (result.isPresent()) return result;
        }
        return Optional.empty();
    }
}
