package com.satyampay.pg.merchant.client;


import com.satyampay.pg.merchant.dto.TransactionStatusUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "TRANSACTION-SERVICE", url = "http://localhost:8086")
public interface TransactionServiceClient {

    @PutMapping("/api/transactions/updateStatus")
    public ResponseEntity<Void> updateStatus(@RequestBody TransactionStatusUpdate dto);
}
