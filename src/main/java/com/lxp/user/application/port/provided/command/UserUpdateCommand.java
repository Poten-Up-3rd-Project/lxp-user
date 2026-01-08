package com.lxp.user.application.port.provided.command;

import com.lxp.common.application.cqrs.Command;
import com.lxp.user.domain.common.model.vo.Level;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.vo.UserName;

import java.util.List;

public record UserUpdateCommand(
    UserId userId, UserName name, Level level, List<Long> tagIds
) implements Command {
}
