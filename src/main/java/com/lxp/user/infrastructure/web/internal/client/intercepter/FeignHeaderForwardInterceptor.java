package com.lxp.user.infrastructure.web.internal.client.intercepter;

import com.lxp.user.infrastructure.constants.PassportConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
public class FeignHeaderForwardInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) {
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getCredentials() != null) {
            String string = String.valueOf(auth.getCredentials());
            log.debug("auth.Credentials: {}", string);
            requestTemplate.header(PassportConstants.PASSPORT_HEADER_NAME, string);
        }
    }
}
