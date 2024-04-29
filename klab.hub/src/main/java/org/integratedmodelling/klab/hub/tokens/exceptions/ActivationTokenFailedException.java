package org.integratedmodelling.klab.hub.tokens.exceptions;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.FORBIDDEN)
public final class ActivationTokenFailedException extends KlabException {

    private static final long serialVersionUID = 2843696817313115417L;

	public ActivationTokenFailedException(String msg) {
        super(msg);
    }

    public ActivationTokenFailedException(String msg, Throwable e) {
        super(msg, e);
    }

    @Override
    public String toString() {
        return "Authentication failed.";
    }
}