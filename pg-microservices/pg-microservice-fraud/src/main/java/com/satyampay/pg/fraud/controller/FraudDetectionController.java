package com.satyampay.pg.fraud.controller;

import com.satyampay.pg.fraud.dto.FraudCheckResult;
import com.satyampay.pg.fraud.dto.FraudDetectionRequest;
import com.satyampay.pg.fraud.dto.FraudDetectionResponse;
import com.satyampay.pg.fraud.dto.Transaction;
import com.satyampay.pg.fraud.model.FraudRecord;
import com.satyampay.pg.fraud.service.FraudDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fraud")
@RequiredArgsConstructor
public class FraudDetectionController {

    private final FraudDetectionService fraudService;

    @PostMapping("/check")
    public ResponseEntity<FraudDetectionResponse> check(@RequestBody FraudDetectionRequest dto) {
        return ResponseEntity.ok(fraudService.check(dto));
    }

    @GetMapping("/merchant/{merchantCode}")
    public ResponseEntity<List<FraudRecord>> getFrauds(@PathVariable String merchantCode) {
        return ResponseEntity.ok(fraudService.getMerchantFrauds(merchantCode));
    }
}
