package com.satyampay.pg.settlement.schedular;

import com.satyampay.pg.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettlementScheduler {

    private final SettlementService settlementService;

    @Scheduled(fixedDelay = 60000) // every 60 seconds
    public void runSettlementBatch() {
        settlementService.processSettlements();
    }
}
