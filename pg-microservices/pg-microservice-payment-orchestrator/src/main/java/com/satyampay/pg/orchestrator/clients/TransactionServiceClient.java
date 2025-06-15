package com.satyampay.pg.orchestrator.clients;

import com.satyampay.pg.orchestrator.dto.TransactionRequest;
import com.satyampay.pg.orchestrator.dto.TransactionStatusUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "TRANSACTION-SERVICE", url = "http://localhost:8086")
public interface TransactionServiceClient {

    @PostMapping("/api/transactions")
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequest dto) ;

    @PutMapping("/api/transactions/{id}/status")
    void updateTransactionStatus(
            @PathVariable("id") String transactionId,
            @RequestBody TransactionStatusUpdate dto
    );
}
