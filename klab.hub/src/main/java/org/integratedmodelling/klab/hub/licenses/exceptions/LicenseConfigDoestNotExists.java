package org.integratedmodelling.klab.hub.licenses.exceptions;


public class LicenseConfigDoestNotExists extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2222945564142109030L;

	public LicenseConfigDoestNotExists(String name) {
		super(String.format("License configuration: %s does not be exist", name));
	}
}
