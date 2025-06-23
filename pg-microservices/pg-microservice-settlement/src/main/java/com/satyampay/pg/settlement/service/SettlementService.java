package com.satyampay.pg.settlement.service;

import com.satyampay.pg.settlement.dto.SettlementResponse;
import com.satyampay.pg.settlement.dto.TransactionSuccessEvent;

import java.util.List;

public interface SettlementService {
    void processSettlements(); // Scheduled
    List<SettlementResponse> getSettlementsForMerchant(String merchantCode);
    void settle(TransactionSuccessEvent event); // Event handler
    public List<SettlementResponse> getAllSettlements();
}
