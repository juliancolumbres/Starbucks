package com.example.springstarbucksapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;


@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue drink() {
        return new Queue("drink");
    }
}