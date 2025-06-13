package com.satyampay.pg.merchant.services;

import com.satyampay.pg.merchant.dto.MerchantRequest;
import com.satyampay.pg.merchant.dto.MerchantResponse;
import com.satyampay.pg.merchant.exception.MerchantNotFoundException;
import com.satyampay.pg.merchant.models.Merchant;
import com.satyampay.pg.merchant.repositories.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public MerchantResponse createMerchant(MerchantRequest dto) {
        Merchant merchant = modelMapper.map(dto, Merchant.class);

        merchant.setMerchantCode(UUID.randomUUID().toString());
        merchant.setApiKey(UUID.randomUUID().toString().replace("-", ""));
        merchant.setKycVerified(false);

        repository.save(merchant);
        return modelMapper.map(merchant, MerchantResponse.class);
    }

    @Override
    public MerchantResponse getMerchantByCode(String code) {
        Merchant merchant = repository.findByMerchantCode(code)
                .orElseThrow(() -> new MerchantNotFoundException(code));
        return modelMapper.map(merchant, MerchantResponse.class);
    }

    @Override
    public MerchantResponse updateMerchant(String code, MerchantRequest dto) {
        Merchant merchant = repository.findByMerchantCode(code)
                .orElseThrow(() -> new MerchantNotFoundException(code));

        modelMapper.map(dto, merchant); // map fields from dto to existing merchant
        return modelMapper.map(repository.save(merchant), MerchantResponse.class);
    }

    @Override
    public void verifyKyc(String code) {
        Merchant merchant = repository.findByMerchantCode(code)
                .orElseThrow(() -> new MerchantNotFoundException(code));
        merchant.setKycVerified(true);
        repository.save(merchant);
    }
}

