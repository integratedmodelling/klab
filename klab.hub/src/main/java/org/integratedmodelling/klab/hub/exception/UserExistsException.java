package org.integratedmodelling.klab.hub.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.integratedmodelling.klab.exceptions.KlabException;

@ResponseStatus(value = org.springframework.http.HttpStatus.CONFLICT)
public class UserExistsException extends KlabException {

    private static final long serialVersionUID = -1332212722467230931L;

    public UserExistsException(String username) {
        this(username, null);
    }

    public UserExistsException(String username, Throwable t) {
        super(String.format("Username '%s' already exists", username), t);
    }
}