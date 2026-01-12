package com.lxp.user.infrastructure.web.external.passport.model;

import java.util.List;

public record PassportClaims(
    String userId,
    List<String> roles,
    String traceId
) {
}
