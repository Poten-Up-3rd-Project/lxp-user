package com.lxp.user.application.port.required;

import com.lxp.user.application.port.required.dto.AuthRegeneratedTokenCommand;
import com.lxp.user.application.port.required.query.AuthRegeneratedTokenResult;

public interface AuthServicePort {

    AuthRegeneratedTokenResult getRegeneratedToken(AuthRegeneratedTokenCommand command);

    void revokeToken(String token);

}
