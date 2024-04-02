package org.integratedmodelling.klab.hub.users.exceptions;

public class UserExistsException extends RuntimeException{

    private static final long serialVersionUID = 3765542602656572848L;

	public UserExistsException(String username) {
        this(username, null);
    }

    public UserExistsException(String username, Throwable t) {
        super(String.format("Username '%s' already exists", username), t);
    }
}