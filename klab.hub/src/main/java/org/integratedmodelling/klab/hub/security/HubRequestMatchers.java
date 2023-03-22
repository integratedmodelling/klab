package org.integratedmodelling.klab.hub.security;

import org.integratedmodelling.klab.api.API;

public final class HubRequestMatchers {
	
	private static final String[] authentication = new String[] {
			API.HUB.AUTHENTICATE_ENGINE,
			API.HUB.AUTHENTICATE_LEVER,
			API.HUB.AUTHENTICATE_NODE,
			API.HUB.AUTHENTICATE_USER,
			API.HUB.LEGACY_AUTHENTICATE_ENGINE
	};
	
	private static final String[] users = new String[] {
			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_ACTIVATION + ".*",
			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_LOST_PASSWORD + ".*",
			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_SET_PASSWORD + ".*",
			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_VERIFICATION + ".*",
			API.HUB.USER_BASE+"$"
	};
	
	private static final String[] agreements = new String[] {
	        API.HUB.AGREEMENT_TEMPLATE_TYPE_LEVEL+"*"            
    };

	public static String[] getAuthentication() {
		return authentication;
	}

	public static String[] getUsers() {
		return users;
	}
	
	public static String[] getAgreements() {
        return agreements;
    }

	
}
