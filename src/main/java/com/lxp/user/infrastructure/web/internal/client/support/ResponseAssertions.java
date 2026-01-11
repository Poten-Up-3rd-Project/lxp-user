package com.lxp.user.infrastructure.web.internal.client.support;

import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;
import org.springframework.http.ResponseEntity;

public class ResponseAssertions {

    private ResponseAssertions() {
    }

    public static void ensure2xx(ResponseEntity<?> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new UserException(UserErrorCode.EXTERNAL_SERVICE_ERROR);
        }
    }

    public static <T> T requireBody(ResponseEntity<T> response) {
        if (response == null || !response.hasBody() || response.getBody() == null) {
            throw new UserException(UserErrorCode.EXTERNAL_SERVICE_ERROR);
        }
        return response.getBody();
    }

    public static <T> T getBodyIf2xx(ResponseEntity<T> response) {
        ensure2xx(response);
        return requireBody(response);
    }

}
