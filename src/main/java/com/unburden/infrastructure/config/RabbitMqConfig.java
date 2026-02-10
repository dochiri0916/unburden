package com.unburden.infrastructure.config;

import com.unburden.infrastructure.config.properties.RabbitMqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final RabbitMqProperties rabbitMqProperties;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitMqProperties.exchange());
    }

    @Bean
    public Queue queue() {
        return new Queue(rabbitMqProperties.queue(), true);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(rabbitMqProperties.routingKey());
    }

}