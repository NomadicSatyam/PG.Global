package com.satyampay.pg.settlement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SettlementMicroservice {

    public static void main(String[] args) { SpringApplication.run(SettlementMicroservice.class, args); }

}
