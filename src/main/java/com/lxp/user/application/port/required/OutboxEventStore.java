package com.lxp.user.application.port.required;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.user.application.event.integration.EventMetadata;

public interface OutboxEventStore {
    void save(IntegrationEvent event, EventMetadata metadata);
}
