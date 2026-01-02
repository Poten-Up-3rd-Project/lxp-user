package com.lxp.user.domain.common.exception;

import com.lxp.common.domain.exception.DomainException;

public class UserException extends DomainException {

    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(UserErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
