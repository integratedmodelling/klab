package org.integratedmodelling.klab.hub.exception;

public class LockedUserException extends RuntimeException {

	private static final long serialVersionUID = -4197979667961148989L;

	public LockedUserException(String username) {
		super(String.format("User %s is locked.", username));
	}
}
