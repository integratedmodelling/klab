package org.integratedmodelling.klab.hub.exception;

public class UserByEmailDoesNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3856637591095666867L;
	
	public UserByEmailDoesNotExistException(String email) {
		this(email, null);
	}

	public UserByEmailDoesNotExistException(String email, Throwable t) {
		super(String.format("A user with the email address '%s' was not found", email), t);
	}

}
