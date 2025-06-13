package com.satyampay.pg.orchestrator.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * Provides a ModelMapper bean for object mapping.
     *
     * @return a new instance of ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

}
