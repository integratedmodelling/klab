package org.integratedmodelling.klab.hub.users.exceptions;

public class GroupDoesNotExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5038767660084417775L;

    public GroupDoesNotExistException(String msg) {
        this(msg, null);
    }

    public GroupDoesNotExistException(String msg, Throwable t) {
        super(msg, t);
    }
}
