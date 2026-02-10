package com.unburden.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
public record RabbitMqProperties(
        String exchange,
        String queue,
        String routingKey
) {
}