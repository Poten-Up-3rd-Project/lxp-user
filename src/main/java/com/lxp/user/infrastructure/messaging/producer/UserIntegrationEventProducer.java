package com.lxp.user.infrastructure.messaging.producer;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.application.port.out.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserIntegrationEventProducer implements IntegrationEventPublisher {

    private static final String EXCHANGE_NAME = "user.events";

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(IntegrationEvent integrationEvent) {
        this.publish(EXCHANGE_NAME, integrationEvent);
    }

    @Override
    public void publish(String topic, IntegrationEvent integrationEvent) {
        String routingKey = integrationEvent.getEventType();
        log.info("Publishing -> exchange={}, routingKey={}, eventId={}, source={}",
            topic, routingKey, integrationEvent.getEventId(), integrationEvent.getSource());

        rabbitTemplate.convertAndSend(topic, routingKey, integrationEvent, m -> {
            m.getMessageProperties().setHeader("eventType", routingKey);
            return m;
        });
    }
}
