package com.satyampay.pg.refund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RefundMicroservice {

    public static void main(String[] args) { SpringApplication.run(RefundMicroservice.class, args); }
}
