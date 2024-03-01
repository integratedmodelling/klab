package org.integratedmodelling.klab.hub.exception;


public class UserNameOrEmailExistsException extends RuntimeException {

	private static final long serialVersionUID = 126704959737865365L;

	public UserNameOrEmailExistsException() {
        this(null);
    }

    public UserNameOrEmailExistsException(Throwable t) {
        super("Username or email address already in use", t);
    }
}