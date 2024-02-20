package org.integratedmodelling.klab.hub.licenses.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
public class LicenseGenerationError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7213649208749728795L;
	
	
	public LicenseGenerationError(String name) {
		super(String.format("License configuration: %s could not be created", name));
	}

}
