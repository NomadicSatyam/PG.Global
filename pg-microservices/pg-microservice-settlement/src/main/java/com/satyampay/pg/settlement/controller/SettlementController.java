package com.satyampay.pg.settlement.controller;

import com.satyampay.pg.settlement.dto.SettlementResponse;
import com.satyampay.pg.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping("/{merchantCode}")
    public ResponseEntity<List<SettlementResponse>> getByMerchant(
            @PathVariable String merchantCode) {
        return ResponseEntity.ok(settlementService.getSettlementsForMerchant(merchantCode));
    }
}
