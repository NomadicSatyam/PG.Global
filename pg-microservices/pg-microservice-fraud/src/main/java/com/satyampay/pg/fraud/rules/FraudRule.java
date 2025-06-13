package com.satyampay.pg.fraud.rules;

import com.satyampay.pg.fraud.dto.Transaction;

import java.util.Optional;

public interface FraudRule {
    Optional<String> evaluate(Transaction dto);
}
