package com.ktd.service.afw.boot.api.exception;

import com.ktd.service.afw.core.exception.AgentException;

public class InjectorException extends AgentException {

    private static final long serialVersionUID = -1L;

    public InjectorException(String message) {
        super(message);
    }

    public InjectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
