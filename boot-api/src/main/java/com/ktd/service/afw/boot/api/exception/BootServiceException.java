package com.ktd.service.afw.boot.api.exception;

import com.ktd.service.afw.core.exception.AgentException;

public class BootServiceException extends AgentException {

    public BootServiceException() {
    }

    public BootServiceException(String message) {
        super(message);
    }

    public BootServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BootServiceException(Throwable cause) {
        super(cause);
    }

    public BootServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
