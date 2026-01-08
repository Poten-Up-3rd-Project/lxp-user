package com.lxp.user.application.port.required;

import com.lxp.user.application.port.required.dto.AuthRegeneratedTokenRequest;
import com.lxp.user.application.port.required.query.AuthRegeneratedTokenResult;

public interface AuthServicePort {

    AuthRegeneratedTokenResult getRegeneratedToken(AuthRegeneratedTokenRequest request);//todo request 이름 변경

    void revokeToken(String token);

}
