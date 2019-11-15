package org.integratedmodelling.klab.hub.exception;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.CONFLICT)
public class TokenGenerationException extends KlabException {

    private static final long serialVersionUID = 2417793672837608112L;

    public TokenGenerationException(String msg) {
        super(msg);
    }
    
    public TokenGenerationException(String msg, Throwable e) {
        super(msg, e);
    }
}