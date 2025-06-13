package com.satyampay.pg.fraud.service;

import com.satyampay.pg.fraud.dto.FraudCheckResult;
import com.satyampay.pg.fraud.dto.Transaction;
import com.satyampay.pg.fraud.model.FraudRecord;

import java.util.List;

public interface FraudDetectionService {

    FraudCheckResult check(Transaction dto);
    List<FraudRecord> getMerchantFrauds(String merchantCode);
}
