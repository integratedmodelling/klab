package org.integratedmodelling.klab.hub.exception;


public class UserEmailExistsException extends RuntimeException {

	private static final long serialVersionUID = 126704959737865365L;

	public UserEmailExistsException(String email) {
        this(email, null);
    }

    public UserEmailExistsException(String email, Throwable t) {
        super(String.format("A user with the email address '%s' already exists", email), t);
    }
}