package com.lxp.user.domain.user.event;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.user.domain.common.event.CrudEvent;

import java.time.LocalDateTime;

public class UserUpdatedEvent extends BaseDomainEvent implements CrudEvent {

    private final String role;

    protected UserUpdatedEvent(String eventId, String userId, LocalDateTime occurredAt, String role) {
        super(eventId, userId, occurredAt);
        this.role = role;
    }


    @Override
    public CrudType getCrudType() {
        return CrudType.UPDATED;
    }
}
