package com.satyampay.pg.refund.service;

import com.satyampay.pg.refund.dto.RefundRequest;
import com.satyampay.pg.refund.dto.RefundResponse;

import java.util.List;

public interface RefundService {

    RefundResponse initiateRefund(RefundRequest dto);
    List<RefundResponse> getRefundsForMerchant(String merchantCode);
}
