package org.integratedmodelling.klab.hub.exception;


public class MailAddressNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = -841321748861453069L;

	public MailAddressNotAllowedException(String email) {
        this(email, null);
    }

    public MailAddressNotAllowedException(String email, Throwable t) {
        super("The address " + email + " cannot send emails", t);
    }
}