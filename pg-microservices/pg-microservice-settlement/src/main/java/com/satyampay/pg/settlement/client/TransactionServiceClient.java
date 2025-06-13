package com.satyampay.pg.settlement.client;

import com.satyampay.pg.settlement.dto.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "TRANSACTION-SERVICE", url = "http://localhost:8086")
public interface TransactionServiceClient {
    @GetMapping("/api/transactions")
    List<Transaction> getAllTransactions(); // Optionally add filters
}
