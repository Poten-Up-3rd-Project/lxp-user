package com.lxp.user.application.port.required;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.domain.event.BaseDomainEvent;

public interface DomainEventToIntegrationEventConverter {
    IntegrationEvent convert(BaseDomainEvent domainEvent);
}
