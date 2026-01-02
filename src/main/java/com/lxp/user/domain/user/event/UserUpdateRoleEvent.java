package com.lxp.user.domain.user.event;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.user.domain.common.event.CrudEvent;

public class UserUpdateRoleEvent extends BaseDomainEvent implements CrudEvent {

    protected UserUpdateRoleEvent(String aggregateId) {
        super(aggregateId);
    }

    @Override
    public CrudType getCrudType() {
        return CrudType.UPDATED;
    }
}
