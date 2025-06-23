package com.satyampay.pg.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MerchantMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(MerchantMicroservice.class, args);
    }
}