package com.satyampay.pg.refund.controller;

import com.satyampay.pg.refund.dto.RefundRequest;
import com.satyampay.pg.refund.dto.RefundResponse;
import com.satyampay.pg.refund.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundResponse> create(@RequestBody RefundRequest dto) {
        return ResponseEntity.ok(refundService.initiateRefund(dto));
    }

    @GetMapping("/merchant/{merchantCode}")
    public ResponseEntity<List<RefundResponse>> getMerchantRefunds(@PathVariable String merchantCode) {
        return ResponseEntity.ok(refundService.getRefundsForMerchant(merchantCode));
    }
}
