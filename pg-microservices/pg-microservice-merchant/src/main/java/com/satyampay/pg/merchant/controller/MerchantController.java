package com.satyampay.pg.merchant.controller;

import com.satyampay.pg.merchant.dto.MerchantRequest;
import com.satyampay.pg.merchant.dto.MerchantResponse;
import com.satyampay.pg.merchant.services.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @PostMapping
    public ResponseEntity<MerchantResponse> create(@Valid @RequestBody MerchantRequest dto) {
        return ResponseEntity.ok(merchantService.createMerchant(dto));
    }

    @GetMapping("/{code}")
    public ResponseEntity<MerchantResponse> get(@PathVariable String code) {
        return ResponseEntity.ok(merchantService.getMerchantByCode(code));
    }

    @PutMapping("/{code}")
    public ResponseEntity<MerchantResponse> update(
            @PathVariable String code,
            @Valid @RequestBody MerchantRequest dto) {
        return ResponseEntity.ok(merchantService.updateMerchant(code, dto));
    }

    @PatchMapping("/{code}/verify-kyc")
    public ResponseEntity<Void> verifyKyc(@PathVariable String code) {
        merchantService.verifyKyc(code);
        return ResponseEntity.noContent().build();
    }
}

