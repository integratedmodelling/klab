package org.integratedmodelling.klab.hub.exception;

public class DeletedUserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4670087352042609325L;
	

    public DeletedUserNotFoundException(String username) {
        this(username, null);
    }

    public DeletedUserNotFoundException(String username, Throwable t) {
        super(String.format("Username '%s' was not found in deleted user database", username), t);
    }

}
