package com.lxp.user.domain.user.event;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.user.domain.common.event.CrudEvent;

import java.util.List;

public class UserCreatedEvent extends BaseDomainEvent implements CrudEvent {
    private final List<Long> tags;
    private final String level;
    private final String job;

    public UserCreatedEvent(String userId, List<Long> tags, String level, String job) {
        super(userId);
        this.tags = tags;
        this.level = level;
        this.job = job;
    }

    @Override
    public CrudType getCrudType() {
        return CrudType.CREATED;
    }
}
