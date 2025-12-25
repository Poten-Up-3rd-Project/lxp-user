package com.lxp.user.infrastructure.messaging;

import com.lxp.common.application.port.in.IntegrationEventHandler;
import com.lxp.common.application.port.out.IntegrationEventPublisher;
import com.lxp.user.application.event.UserCreationFailedIntegrationEvent;
import com.lxp.user.application.event.UserRegisteredIntegrationEvent;
import com.lxp.user.application.in.usecase.UserSaveUseCase;
import com.lxp.user.infrastructure.messaging.mapper.IntegrationEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredIntegrationEventConsumer implements IntegrationEventHandler<UserRegisteredIntegrationEvent> {

    private final UserSaveUseCase userSaveUseCase;
    private final IntegrationEventPublisher integrationEventPublisher;
    private final IntegrationEventMapper eventMapper;

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.auth-events}")
    public void handle(UserRegisteredIntegrationEvent userRegisteredIntegrationEvent) {
        try {
            userSaveUseCase.execute(eventMapper.toSaveCommand(userRegisteredIntegrationEvent));
            log.info("Successfully created user: userId={}", userRegisteredIntegrationEvent.getData().userId());
        } catch (Exception e) {
            log.error("Failed to create user: userId={}, error={}",
                userRegisteredIntegrationEvent.getData().userId(), e.getMessage(), e);

            publishCompensationEvent(userRegisteredIntegrationEvent, e);
        }
    }

    private void publishCompensationEvent(
        UserRegisteredIntegrationEvent originalEvent,
        Exception error
    ) {
        try {
            UserCreationFailedIntegrationEvent compensationEvent = eventMapper.toCompensationEvent(
                originalEvent, error.getMessage()
            );
            integrationEventPublisher.publish(compensationEvent);

            log.info("Published compensation event: userId={}", originalEvent.getData().userId());
        } catch (Exception e) {
            log.error("Failed to publish compensation event: userId={}", originalEvent.getData().userId(), e);
        }
    }

    @Override
    public Class<UserRegisteredIntegrationEvent> supportedEventType() {
        return UserRegisteredIntegrationEvent.class;
    }
}
