package com.satyampay.pg.refund.client;

import com.satyampay.pg.refund.dto.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "TRANSACTION-SERVICE", url = "http://localhost:8086")
public interface TransactionServiceClient {

    @GetMapping("/api/transactions/{id}")
    Transaction getTransaction(@PathVariable("id") String id);
}
