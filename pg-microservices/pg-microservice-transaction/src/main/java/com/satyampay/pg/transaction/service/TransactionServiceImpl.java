package com.satyampay.pg.transaction.service;

import com.satyampay.pg.transaction.dto.TransactionRequest;
import com.satyampay.pg.transaction.dto.TransactionResponse;
import com.satyampay.pg.transaction.dto.TransactionStatusUpdate;
import com.satyampay.pg.transaction.exception.TransactionNotFoundException;
import com.satyampay.pg.transaction.model.Transaction;
import com.satyampay.pg.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    @Override
    public void createTransaction(TransactionRequest dto) {
        Transaction transaction = Transaction.builder()
                .merchantTransactionId(dto.getMerchantTransactionId())
                .transactionId(dto.getTransactionId())
                .merchantCode(dto.getMerchantCode())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .paymentMode(dto.getPaymentMode())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        repository.save(transaction);
    }

    @Override
    public void updateStatus(TransactionStatusUpdate dto) {
        Transaction transaction = repository.findById(dto.getTransactionId())
                .orElseThrow(() -> new TransactionNotFoundException(dto.getTransactionId()));
        transaction.setStatus(dto.getStatus());
        transaction.setPaymentReference(dto.getPaymentReference());
        transaction.setFailureReason(dto.getFailureReason());
        transaction.setUpdatedAt(LocalDateTime.now());
        repository.save(transaction);
    }

    @Override
    public TransactionResponse getById(String transactionId) {
        Transaction tx = repository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
        return TransactionResponse.builder()
                .transactionId(tx.getTransactionId())
                .merchantCode(tx.getMerchantCode())
                .status(tx.getStatus())
                .paymentMode(tx.getPaymentMode())
                .amount(tx.getAmount())
                .currency(tx.getCurrency())
                .paymentReference(tx.getPaymentReference())
                .failureReason(tx.getFailureReason())
                .createdAt(tx.getCreatedAt())
                .updatedAt(tx.getUpdatedAt())
                .build();
    }

    @Override
    public void updateTransactionStatus(String transactionId, TransactionStatusUpdate dto) {
        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
        transaction.setStatus(dto.getStatus());
        transaction.setPaymentReference(dto.getPaymentReference());
        transaction.setFailureReason(dto.getFailureReason());
        transaction.setUpdatedAt(LocalDateTime.now());
        repository.save(transaction);
    }
}
