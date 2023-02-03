package org.integratedmodelling.klab.hub.exception;

public class SuspendedUserException extends RuntimeException {

	private static final long serialVersionUID = -4197979667961148989L;

	public SuspendedUserException(String username) {
		super(String.format("User %s is suspended.", username));
	}
}
