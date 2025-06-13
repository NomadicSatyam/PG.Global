package com.satyampay.pg.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class OrchestratorMicroservice {

    public static void main(String[] args) { SpringApplication.run(OrchestratorMicroservice.class, args); }

}
