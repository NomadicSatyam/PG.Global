package com.satyampay.pg.settlement.service;

import com.satyampay.pg.settlement.dto.SettlementResponse;

import java.util.List;

public interface SettlementService {
    void processSettlements(); // Scheduled
    List<SettlementResponse> getSettlementsForMerchant(String merchantCode);
}
