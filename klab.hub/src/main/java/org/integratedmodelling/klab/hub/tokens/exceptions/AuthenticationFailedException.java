package org.integratedmodelling.klab.hub.tokens.exceptions;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.FORBIDDEN)
public final class AuthenticationFailedException extends KlabException {

   private static final long serialVersionUID = -5724804803097691001L;

	public AuthenticationFailedException(String msg) {
        super(msg);
    }

    public AuthenticationFailedException(String msg, Throwable e) {
        super(msg, e);
    }

    @Override
    public String toString() {
        return "Authentication failed.";
    }
}