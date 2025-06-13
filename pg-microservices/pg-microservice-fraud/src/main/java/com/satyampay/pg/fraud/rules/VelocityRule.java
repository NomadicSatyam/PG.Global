package com.satyampay.pg.fraud.rules;

import com.satyampay.pg.fraud.dto.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VelocityRule implements FraudRule {
    private final Map<String, List<LocalDateTime>> merchantTxnLog = new ConcurrentHashMap<>();

    @Override
    public Optional<String> evaluate(Transaction dto) {
        merchantTxnLog.putIfAbsent(dto.getMerchantCode(), new ArrayList<>());
        List<LocalDateTime> timestamps = merchantTxnLog.get(dto.getMerchantCode());

        LocalDateTime now = LocalDateTime.now();
        timestamps.add(now);

        // Keep only last 1 minute's entries
        timestamps.removeIf(t -> t.isBefore(now.minusMinutes(1)));

        if (timestamps.size() > 5) {
            return Optional.of("Velocity check failed: Too many transactions in 1 minute");
        }

        return Optional.empty();
    }
}
