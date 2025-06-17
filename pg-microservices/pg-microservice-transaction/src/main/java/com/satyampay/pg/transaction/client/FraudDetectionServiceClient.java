package com.satyampay.pg.transaction.client;

import com.satyampay.pg.transaction.dto.FraudCheckResult;
import com.satyampay.pg.transaction.model.Transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FraudDetectionServiceClient {

    @PostMapping("/api/fraud/check")
    public ResponseEntity<FraudCheckResult> check(@RequestBody Transaction dto);
}
