package com.satyampay.pg.merchant.repositories;

import com.satyampay.pg.merchant.models.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends MongoRepository<Merchant,String> {

    Optional<Merchant> findByMerchantCode(String code);
}
