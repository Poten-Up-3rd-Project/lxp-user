package com.lxp.user.infrastructure.web.internal.client.support;

import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;
import org.springframework.http.ResponseEntity;

public class ResponseUnwrapper {

    private ResponseUnwrapper() {
    }

    public static <T> T unwrapResponse(ResponseEntity<T> response) {
        if (!response.getStatusCode().is2xxSuccessful() || !response.hasBody() || response.getBody() == null) {
            throw new UserException(UserErrorCode.EXTERNAL_SERVICE_ERROR);
        }

        return response.getBody();
    }

}
