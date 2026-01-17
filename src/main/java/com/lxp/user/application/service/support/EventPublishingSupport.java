package com.lxp.user.application.service.support;

import com.lxp.common.application.port.out.DomainEventPublisher;
import com.lxp.common.domain.event.AggregateRoot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublishingSupport {

    private final DomainEventPublisher domainEventPublisher;

    public void publishAndClear(AggregateRoot<?> aggregate) {
        aggregate.getDomainEvents().forEach(domainEventPublisher::publish);
        aggregate.clearDomainEvents();
    }
}
