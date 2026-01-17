package com.lxp.user.infrastructure.outbox;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxEvent;
import com.lxp.user.application.event.integration.EventMetadata;
import com.lxp.user.application.port.required.OutboxEventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventStoreAdapter implements OutboxEventStore {

    private final UserOutboxRepository outboxRepository;
    private final OutboxEventSerializer serializer;

    @Override
    public void save(IntegrationEvent event, EventMetadata metadata) {
        OutboxEvent outbox = new OutboxEvent(
            event.getEventId(),
            event.getEventType(),
            metadata.aggregateEventType(),
            metadata.aggregateId(),
            serializer.serialize(event),
            event.getOccurredAt()
        );
        outboxRepository.save(outbox);

    }
}
