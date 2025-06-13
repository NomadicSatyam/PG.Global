package com.satyampay.pg.refund.service;

import com.satyampay.pg.refund.client.TransactionServiceClient;
import com.satyampay.pg.refund.dto.RefundRequest;
import com.satyampay.pg.refund.dto.RefundResponse;
import com.satyampay.pg.refund.dto.Transaction;
import com.satyampay.pg.refund.model.Refund;
import com.satyampay.pg.refund.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final TransactionServiceClient transactionClient;

    @Override
    public RefundResponse initiateRefund(RefundRequest dto) {
        Transaction tx = transactionClient.getTransaction(dto.getTransactionId());

        if (!"SUCCESS".equalsIgnoreCase(tx.getStatus())) {
            throw new IllegalStateException("Refunds allowed only for successful transactions");
        }

        Refund refund = Refund.builder()
                .transactionId(tx.getTransactionId())
                .merchantCode(tx.getMerchantCode())
                .refundAmount(dto.getRefundAmount())
                .reason(dto.getReason())
                .status("REFUNDED") // Simulating instant refund (for now)
                .refundReference("REF-" + UUID.randomUUID())
                .requestedAt(LocalDateTime.now())
                .processedAt(LocalDateTime.now())
                .build();

        refundRepository.save(refund);

        return toResponse(refund);
    }

    @Override
    public List<RefundResponse> getRefundsForMerchant(String merchantCode) {
        return refundRepository.findByMerchantCode(merchantCode).stream()
                .map(this::toResponse)
                .toList();
    }

    private RefundResponse toResponse(Refund r) {
        return RefundResponse.builder()
                .id(r.getId())
                .transactionId(r.getTransactionId())
                .merchantCode(r.getMerchantCode())
                .refundAmount(r.getRefundAmount())
                .status(r.getStatus())
                .refundReference(r.getRefundReference())
                .reason(r.getReason())
                .requestedAt(r.getRequestedAt())
                .processedAt(r.getProcessedAt())
                .build();
    }
}
