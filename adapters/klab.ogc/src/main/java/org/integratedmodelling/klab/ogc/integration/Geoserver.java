package org.integratedmodelling.klab.ogc.integration;

public enum Geoserver {
	
	INSTANCE;

	public static boolean isEnabled() {
		// TODO check for configuration and handshake
		return false;
	}
}
