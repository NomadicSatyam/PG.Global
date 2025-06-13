package com.satyampay.pg.netbanking.controller;

import com.satyampay.pg.netbanking.dto.PaymentRequest;
import com.satyampay.pg.netbanking.service.NetBankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class NetBankingController {
    private final NetBankingService service;

    @PostMapping("/{transactionId}")
    public ResponseEntity<String> initiateNetBanking(
            @PathVariable String transactionId,
            @RequestBody PaymentRequest dto) {

        service.processNetBanking(transactionId, dto);
        return ResponseEntity.ok("NetBanking payment initiated");
    }
}
