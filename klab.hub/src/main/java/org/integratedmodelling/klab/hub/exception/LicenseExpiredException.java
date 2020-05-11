package org.integratedmodelling.klab.hub.exception;

public class LicenseExpiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3933414074826196862L;

	public LicenseExpiredException(String user) {
		super(String.format("License for %s has expired.  Please download a new certificate", user));
	}
}
