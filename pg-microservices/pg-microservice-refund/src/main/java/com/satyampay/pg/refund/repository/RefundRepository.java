package com.satyampay.pg.refund.repository;

import com.satyampay.pg.refund.model.Refund;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RefundRepository extends MongoRepository<Refund, String> {

    List<Refund> findByMerchantCode(String merchantCode);
}
