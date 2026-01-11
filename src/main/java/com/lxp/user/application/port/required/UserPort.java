package com.lxp.user.application.port.required;

import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.entity.User;

public interface UserPort {

    void save(User user);

    void saveWithProfile(User user);

    void deactivate(User user);

    User loadById(UserId userId);

}
