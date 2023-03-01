package org.integratedmodelling.klab.api.services;

public interface KResolver {
	
	/**
	 * All services publish capabilities and have a call to obtain them.
	 * 
	 * @author Ferd
	 *
	 */
	interface Capabilities {

	}

	Capabilities getCapabilities();
	

}
