package com.satyampay.pg.fraud.service;

import com.satyampay.pg.fraud.dto.FraudCheckResult;
import com.satyampay.pg.fraud.dto.FraudDetectionRequest;
import com.satyampay.pg.fraud.dto.FraudDetectionResponse;
import com.satyampay.pg.fraud.dto.Transaction;
import com.satyampay.pg.fraud.model.FraudRecord;

import java.util.List;

public interface FraudDetectionService {

    FraudDetectionResponse check(FraudDetectionRequest dto);
    List<FraudRecord> getMerchantFrauds(String merchantCode);
}
