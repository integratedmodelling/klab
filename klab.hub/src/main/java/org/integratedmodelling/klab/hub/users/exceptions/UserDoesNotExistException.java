package org.integratedmodelling.klab.hub.users.exceptions;

public class UserDoesNotExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7649471605160771449L;
	
    public UserDoesNotExistException() {
        this(new Throwable());
    }
    
    public UserDoesNotExistException(String username) {
        this(username, null);
    }

    public UserDoesNotExistException(Throwable t) {
        super(String.format("A user with that name does not exist"), t);
    }
    
    public UserDoesNotExistException(String username, Throwable t) {
        super(String.format("A user with that name does not exist", username), t);
    }
    
}
