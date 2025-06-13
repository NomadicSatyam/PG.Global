package com.satyampay.pg.fraud.rules;

import com.satyampay.pg.fraud.dto.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AmountRule implements FraudRule {
    @Override
    public Optional<String> evaluate(Transaction dto) {
        if (dto.getAmount() > 100000) {
            return Optional.of("Amount exceeds transaction threshold");
        }
        return Optional.empty();
    }
}
