package com.lxp.user.application.event.policy;

import com.lxp.common.application.port.out.IntegrationEventPublisher;
import com.lxp.user.application.port.required.OutboxEventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultIntegrationEventRegistry implements IntegrationEventRegistry {

    private final OutboxEventStore outboxStore;
    private final IntegrationEventPublisher eventPublisher;

    @Override
    public void register(IntegrationEventPublishCommand command) {
        switch (command.policy()) {
            case OUTBOX_REQUIRED -> outboxStore.save(command.event(), command.metadata());
            case FIRE_AND_FORGET -> eventPublisher.publish(command.event());
        }
    }
}
