package com.satyampay.pg.fraud.repository;

import com.satyampay.pg.fraud.model.FraudRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FraudRecordRepository extends MongoRepository<FraudRecord, String> {

    List<FraudRecord> findByMerchantCode(String merchantCode);
}
