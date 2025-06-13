package com.satyampay.pg.settlement.repository;

import com.satyampay.pg.settlement.model.Settlement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettlementRepository extends MongoRepository<Settlement,String> {

    Optional<Settlement> findByTransactionId(String transactionId);
}
