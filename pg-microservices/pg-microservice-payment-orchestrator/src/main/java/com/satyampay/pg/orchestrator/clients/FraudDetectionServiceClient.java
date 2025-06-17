package com.satyampay.pg.orchestrator.clients;

import com.satyampay.pg.orchestrator.dto.FraudDetectionRequest;
import com.satyampay.pg.orchestrator.dto.FraudDetectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "FRAUD-DETECTION-SERVICE", url = "http://localhost:8088")
public interface FraudDetectionServiceClient {

    @PostMapping("/api/fraud/check")
    public FraudDetectionResponse checkTransaction(@RequestBody FraudDetectionRequest dto);
}
