package com.example.testmarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ru.csv.order_management")
public class Config {

    @Bean
    public ObjectMapper im(){
        return new ObjectMapper();
    }
}
