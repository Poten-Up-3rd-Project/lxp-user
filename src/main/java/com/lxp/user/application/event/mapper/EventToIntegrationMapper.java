package com.lxp.user.application.event.mapper;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.domain.event.BaseDomainEvent;

public interface EventToIntegrationMapper<T extends BaseDomainEvent> {
    IntegrationEvent map(T event);
}
