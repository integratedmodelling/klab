package org.integratedmodelling.klab.api.auth;

/**
 * Defines constants for common HTTP headers.
 *
 * <p>This interface provides a set of predefined header names used
 * in HTTP requests and responses. These constants can be used to
 * avoid hardcoding string values and reduce errors.</p>
 * 
 * @author Kristina
 */

public interface KlabHttpHeaders {
	
	/**
	 *  Designed to send session information with requests.
	 **/
	public static final String KLAB_AUTHORIZATION = "Klab_Authorization"; 

}
