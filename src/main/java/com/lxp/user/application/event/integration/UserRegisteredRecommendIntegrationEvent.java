package com.lxp.user.application.event.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lxp.common.application.event.BaseIntegrationEventEnvelope;
import com.lxp.user.application.event.payload.UserCreatedPayload;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisteredRecommendIntegrationEvent extends BaseIntegrationEventEnvelope<UserCreatedPayload> {

    private static final String SOURCE = "lxp.user.service";

    public UserRegisteredRecommendIntegrationEvent(@JsonProperty("eventId") String eventId,
                                                   @JsonProperty("occurredAt") LocalDateTime occurredAt,
                                                   @JsonProperty("correlationId") String correlationId,
                                                   @JsonProperty("causationId") String causationId,
                                                   @JsonProperty("payload") UserCreatedPayload payload) {
        super(eventId, occurredAt, SOURCE, correlationId, causationId, payload);
    }

    @Override
    @JsonProperty("eventType")
    public String getEventType() {
        return "user.created";
    }
}
