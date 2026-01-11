package com.lxp.user.application.port.provided.command;

import com.lxp.common.application.cqrs.Command;

public record UserWithdrawnCommand(String userId, String token) implements Command {
}
