package org.integratedmodelling.klab.hub.exception;

public class UserDoesNotExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7649471605160771449L;
	
    public UserDoesNotExistException() {
        this(null);
    }

    public UserDoesNotExistException(Throwable t) {
        super(String.format("A user with that name does not exist"), t);
    }

}
