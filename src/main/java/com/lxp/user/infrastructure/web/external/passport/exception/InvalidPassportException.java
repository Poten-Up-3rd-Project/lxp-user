package com.lxp.user.infrastructure.web.external.passport.exception;

import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;

public class InvalidPassportException extends UserException {

    public InvalidPassportException(String message) {
        super(UserErrorCode.UNAUTHORIZED_ACCESS, message);
    }
}
