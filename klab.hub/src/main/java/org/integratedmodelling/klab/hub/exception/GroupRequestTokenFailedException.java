package org.integratedmodelling.klab.hub.exception;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.FORBIDDEN)
public final class GroupRequestTokenFailedException extends KlabException {

    private static final long serialVersionUID = 1949675863417776169L;

    public GroupRequestTokenFailedException(String msg) {
        super(msg);
    }

    public GroupRequestTokenFailedException(String msg, Throwable e) {
        super(msg, e);
    }

    @Override
    public String toString() {
        return "Authentication failed.";
    }
}