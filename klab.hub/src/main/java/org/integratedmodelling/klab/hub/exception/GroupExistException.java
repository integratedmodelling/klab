package org.integratedmodelling.klab.hub.exception;

public class GroupExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5038767660084417775L;

    public GroupExistException(String msg) {
        this(msg, null);
    }

    public GroupExistException(String msg, Throwable t) {
        super(msg, t);
    }
}
