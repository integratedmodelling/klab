package org.integratedmodelling.klab.hub.exception;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
public final class ChangePasswordTokenFailedException extends KlabException {

    private static final long serialVersionUID = 1949675863417776169L;

    public ChangePasswordTokenFailedException(String msg) {
        super(msg);
    }

    public ChangePasswordTokenFailedException(String msg, Throwable e) {
        super(msg, e);
    }

    @Override
    public String toString() {
        return "Authentication failed.";
    }
}