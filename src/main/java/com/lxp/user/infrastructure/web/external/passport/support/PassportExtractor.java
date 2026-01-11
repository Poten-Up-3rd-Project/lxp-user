package com.lxp.user.infrastructure.web.external.passport.support;

import com.lxp.user.infrastructure.constants.PassportConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class PassportExtractor {

    public String extract(HttpServletRequest request) {
        return request.getHeader(PassportConstants.PASSPORT_HEADER_NAME);
    }
}
