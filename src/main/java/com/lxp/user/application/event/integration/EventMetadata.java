package com.lxp.user.application.event.integration;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.user.domain.common.event.CrudEvent;

public record EventMetadata(
    String aggregateId,
    String aggregateEventType,
    String destination
) {
    public static EventMetadata from(CrudEvent event, String destination) {
        return new EventMetadata(
            event.getAggregateId(),
            event.getClass().getSimpleName(),
            destination
        );
    }

    public static EventMetadata from(BaseDomainEvent event, String destination) {
        return new EventMetadata(
            event.getAggregateId(),
            event.getClass().getSimpleName(),
            destination
        );
    }
}
