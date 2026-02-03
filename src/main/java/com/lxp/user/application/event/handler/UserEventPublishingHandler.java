package com.lxp.user.application.event.handler;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.user.application.event.integration.EventMetadata;
import com.lxp.user.infrastructure.messaging.mapper.IntegrationEventMapper;
import com.lxp.user.application.event.policy.DeliveryPolicy;
import com.lxp.user.application.event.policy.DeliveryPolicyResolver;
import com.lxp.user.application.event.policy.IntegrationEventPublishCommand;
import com.lxp.user.application.event.policy.IntegrationEventRegistry;
import com.lxp.user.domain.common.event.CrudEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserEventPublishingHandler {

    private final IntegrationEventRegistry registry;
    private final IntegrationEventMapper mapper;
    private final DeliveryPolicyResolver policyResolver;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleBeforeCommit(CrudEvent event) {
        BaseDomainEvent domainEvent = (BaseDomainEvent) event;
        DeliveryPolicy policy = policyResolver.resolve(domainEvent);

        if (policy == DeliveryPolicy.OUTBOX_REQUIRED) {
            List<IntegrationEvent> integrationEvents = mapper.toIntegrationEvents(domainEvent);
            for (IntegrationEvent integrationEvent : integrationEvents) {
                registry.register(IntegrationEventPublishCommand.outbox(
                        integrationEvent,
                        EventMetadata.from(domainEvent, "user.events")
                ));
            }
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommit(CrudEvent event) {
        BaseDomainEvent domainEvent = (BaseDomainEvent) event;
        DeliveryPolicy policy = policyResolver.resolve(domainEvent);

        if (policy == DeliveryPolicy.FIRE_AND_FORGET) {
            List<IntegrationEvent> integrationEvents = mapper.toIntegrationEvents(domainEvent);
            for (IntegrationEvent integrationEvent : integrationEvents) {
                registry.register(IntegrationEventPublishCommand.fireAndForget(
                        integrationEvent
                ));
            }
        }
    }
}
