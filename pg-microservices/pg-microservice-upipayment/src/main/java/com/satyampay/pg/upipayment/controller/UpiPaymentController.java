package com.satyampay.pg.upipayment.controller;

import com.satyampay.pg.upipayment.dto.PaymentRequest;
import com.satyampay.pg.upipayment.service.UpiPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class UpiPaymentController {
    private final UpiPaymentService service;

    @PostMapping("/{transactionId}")
    public ResponseEntity<String> initiateUpiPayment(
            @PathVariable String transactionId,
            @RequestBody PaymentRequest dto) {

        service.processUpiPayment(transactionId, dto);
        return ResponseEntity.ok("UPI payment initiated");
    }
}
