package com.lxp.user.application.out;

import com.lxp.user.domain.user.model.entity.User;

public interface UserCommandPort {

    void save(User user);

    void saveWithProfile(User user);

    void deactivate(User user);
}
