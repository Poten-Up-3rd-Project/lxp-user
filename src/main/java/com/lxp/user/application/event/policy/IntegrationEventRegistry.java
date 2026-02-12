package com.lxp.user.application.event.policy;

public interface IntegrationEventRegistry {
    void register(IntegrationEventPublishCommand command);
}
