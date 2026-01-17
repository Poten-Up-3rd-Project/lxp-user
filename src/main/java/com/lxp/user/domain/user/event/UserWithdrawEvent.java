package com.lxp.user.domain.user.event;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.user.domain.common.event.CrudEvent;

public class UserWithdrawEvent extends BaseDomainEvent implements CrudEvent {

    public UserWithdrawEvent(String aggregateId) {
        super(aggregateId);
    }

    @Override
    public CrudType getCrudType() {
        return CrudType.DELETED;
    }
}
