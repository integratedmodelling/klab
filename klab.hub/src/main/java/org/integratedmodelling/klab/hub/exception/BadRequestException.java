package org.integratedmodelling.klab.hub.exception;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
public final class BadRequestException extends KlabException {

    private static final long serialVersionUID = -3921597449342910649L;

    public BadRequestException(String msg) {
        this(msg, null);
    }

    public BadRequestException(String msg, Throwable t) {
        super(msg, t);
    }
}
