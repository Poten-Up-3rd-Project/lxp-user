package com.lxp.user.domain.user.event;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.user.domain.common.event.CrudEvent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserCreatedEvent extends BaseDomainEvent implements CrudEvent {
    private final List<Long> tags;
    private final String level;

    public UserCreatedEvent(String userId, List<Long> tags, String level) {
        super(userId);
        this.tags = tags;
        this.level = level;
    }

    @Override
    public CrudType getCrudType() {
        return CrudType.CREATED;
    }
}
