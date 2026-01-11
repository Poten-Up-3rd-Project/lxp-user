package com.lxp.user.application.port.required.command;

import java.util.List;

public record AuthRegeneratedCommand(
    String email, String token, List<String> role
) {
}
