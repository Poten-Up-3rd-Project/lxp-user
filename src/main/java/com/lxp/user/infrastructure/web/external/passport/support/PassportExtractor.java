package com.lxp.user.infrastructure.web.external.passport.support;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class PassportExtractor {

    private static final String PASSPORT_HEADER = "X-Passport";

    public String extract(HttpServletRequest request) {
        return request.getHeader(PASSPORT_HEADER);
    }
}
