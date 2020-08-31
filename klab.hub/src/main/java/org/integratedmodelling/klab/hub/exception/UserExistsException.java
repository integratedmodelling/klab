package org.integratedmodelling.klab.hub.exception;

public class UserExistsException extends RuntimeException{

    private static final long serialVersionUID = -1332212722467230931L;

    public UserExistsException(String username) {
        this(username, null);
    }

    public UserExistsException(String username, Throwable t) {
        super(String.format("Username '%s' already exists", username), t);
    }
}