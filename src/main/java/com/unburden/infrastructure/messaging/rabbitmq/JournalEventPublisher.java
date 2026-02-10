package com.unburden.infrastructure.messaging.rabbitmq;

import com.unburden.application.journal.event.JournalWrittenEvent;
import com.unburden.infrastructure.config.properties.RabbitMqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JournalEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;

    public void publish(JournalWrittenEvent event) {
        rabbitTemplate.convertAndSend(
                rabbitMqProperties.exchange(),
                rabbitMqProperties.routingKey(),
                event
        );
    }

}