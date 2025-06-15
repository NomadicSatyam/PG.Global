package com.satyampay.pg.cardpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CardPaymentMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(CardPaymentMicroservice.class, args);
    }
}
