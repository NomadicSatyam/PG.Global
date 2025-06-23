package com.satyampay.pg.transaction.controller;

import com.satyampay.pg.transaction.dto.TransactionRequest;
import com.satyampay.pg.transaction.dto.TransactionResponse;
import com.satyampay.pg.transaction.dto.TransactionStatusUpdate;
import com.satyampay.pg.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequest dto) {
        transactionService.createTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<Void> updateStatus(@RequestBody TransactionStatusUpdate dto) {
        transactionService.updateStatus(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> get(@PathVariable String id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateTransactionStatus(
            @PathVariable String id,
            @RequestBody TransactionStatusUpdate dto) {
        transactionService.updateTransactionStatus(id, dto);
        return ResponseEntity.ok().build();
    }
}
