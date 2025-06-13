package com.satyampay.pg.cardpayment.controller;

import com.satyampay.pg.cardpayment.dto.PaymentRequest;
import com.satyampay.pg.cardpayment.service.CardPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class CardPaymentController {
    private final CardPaymentService service;

    @PostMapping("/{transactionId}")
    public ResponseEntity<String> initiateCardPayment(
            @PathVariable String transactionId,
            @RequestBody PaymentRequest dto) {

        service.processCardPayment(transactionId, dto);
        return ResponseEntity.ok("Card payment initiated");
    }
}
