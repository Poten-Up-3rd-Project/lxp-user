package com.lxp.user.infrastructure.messaging.publisher;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.application.port.out.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserIntegrationEventPublisher implements IntegrationEventPublisher {

    private static final String EXCHANGE_NAME = "user.events";

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(IntegrationEvent integrationEvent) {
        this.publish(EXCHANGE_NAME, integrationEvent);
    }

    @Override
    public void publish(String topic, IntegrationEvent integrationEvent) {
        rabbitTemplate.convertAndSend(topic, integrationEvent.getEventType(), integrationEvent);
    }
}
