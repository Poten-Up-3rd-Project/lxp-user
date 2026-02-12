package com.lxp.user.domain.user.event;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.user.domain.common.event.CrudEvent;
import lombok.Getter;

import java.util.List;

@Getter
public class UserUpdatedEvent extends BaseDomainEvent implements CrudEvent {
    private final String name;
    private final String email;
    private final List<Long> tags;
    private final String level;
    private final UpdateType updateType;

    public UserUpdatedEvent(String aggregateId, String name, String email, List<Long> tags, String level, UpdateType updateType) {
        super(aggregateId);
        this.name = name;
        this.email = email;
        this.tags = tags;
        this.level = level;
        this.updateType = updateType;
    }

    @Override
    public CrudType getCrudType() {
        return CrudType.UPDATED;
    }

    public static UpdateType findType(boolean userUpdated, boolean profileUpdated) {
        return userUpdated && profileUpdated ? UserUpdatedEvent.UpdateType.ALL :
            userUpdated ? UserUpdatedEvent.UpdateType.USER : UserUpdatedEvent.UpdateType.PROFILE;
    }

    public enum UpdateType {
        USER, PROFILE, ALL
    }
}
