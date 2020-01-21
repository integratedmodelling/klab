package org.integratedmodelling.klab.hub.exception;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.CONFLICT)
public class UserEmailExistsException extends KlabException {

    private static final long serialVersionUID = -1332212722467230931L;

    public UserEmailExistsException(String email) {
        this(email, null);
    }

    public UserEmailExistsException(String email, Throwable t) {
        super(String.format("A user with the email address '%s' already exists", email), t);
    }
}