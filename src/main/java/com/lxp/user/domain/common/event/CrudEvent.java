package com.lxp.user.domain.common.event;

import com.lxp.common.domain.event.DomainEvent;

public interface CrudEvent extends DomainEvent {

    CrudType getCrudType();

    enum CrudType {
        CREATED, UPDATED, DELETED, FAILED
    }
}
