package org.integratedmodelling.klab.hub.exception;

public class LicenseConfigDefaultNotFound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6387537446275692176L;
	
	public LicenseConfigDefaultNotFound() {
		super("No Default License configuration found.");
	}

}
