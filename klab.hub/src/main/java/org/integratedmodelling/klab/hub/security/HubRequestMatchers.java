//package org.integratedmodelling.klab.hub.security;
//
//import org.integratedmodelling.klab.api.API;
//
//public final class HubRequestMatchers {
//	
//	private static final String[] authentication = new String[] {
//			API.HUB.AUTHENTICATE_ENGINE,
//			API.HUB.AUTHENTICATE_LEVER,
//			API.HUB.AUTHENTICATE_NODE,
//			API.HUB.AUTHENTICATE_USER,
//			API.HUB.LEGACY_AUTHENTICATE_ENGINE
//	};
//	
//	private static final String[] users = new String[] {
//			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_ACTIVATION + ".*",
//			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_LOST_PASSWORD + ".*",
//			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_SET_PASSWORD + ".*",
//			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_VERIFICATION + ".*",
//			API.HUB.USER_BASE+"$"
//	};
//
//	public static String[] getAuthentication() {
//		return authentication;
//	}
//
//	public static String[] getUsers() {
//		return users;
//	}
//
//	
//}

import org.integratedmodelling.klab.api.API;

public final class HubRequestMatchers {
	
	private static final String[] authentication = new String[] {
			API.HUB.AUTHENTICATE_ENGINE,
			API.HUB.AUTHENTICATE_LEVER,
			API.HUB.AUTHENTICATE_NODE,
			API.HUB.AUTHENTICATE_USER,
	};
	
	private static final String[] usersPost = new String[] {
			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_ACTIVATION + ".*",
			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_LOST_PASSWORD + ".*",
			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_SET_PASSWORD + ".*",
			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_VERIFICATION + ".*",
			API.HUB.USER_BASE+"$"
	};
	
	private static final String[] usersPut = new String[] {
			API.HUB.USER_BASE+".*" + API.HUB.PARAMETERS.USER_SET_EMAIL + ".*",			
			API.HUB.USER_BASE+"$"
	};
	
	private static final String[] agreements = new String[] {
	        API.HUB.AGREEMENT_TEMPLATE_TYPE_LEVEL+"*"            
    };
	
	private static final String[] usersGet = new String[] {
			API.HUB.USER_BASE_NOAUTH+".*" + API.HUB.PARAMETERS.USER_GET + ".*",			
			API.HUB.USER_BASE+"$"
	};

	public static String[] getAuthentication() {
		return authentication;
	}

	public static String[] getUsersPost() {
		return usersPost;
	}
	
	public static String[] getAgreements() {
        return agreements;
    }

	public static String[] getUsersGet() {
		return usersGet;
	}

	public static String[] getUsersPut() {
		return usersPut;
	}

	
}
