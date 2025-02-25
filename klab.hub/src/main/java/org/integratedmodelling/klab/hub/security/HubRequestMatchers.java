package org.integratedmodelling.klab.hub.security;

import org.integratedmodelling.klab.api.API;

public final class HubRequestMatchers {
	
    private static final String[] authentication = new String[]{API.HUB.AUTHENTICATE_ENGINE, API.HUB.AUTHENTICATE_LEVER,
            API.HUB.AUTHENTICATE_NODE};
	
    private static final String[] agreements = new String[]{API.HUB.AGREEMENT_TEMPLATE_TYPE_LEVEL + "*"};
	
    private static final String[] ui = new String[]{API.HUB.UI + "*"};
    
    private static final String[] node = new String[] {API.HUB.NODE_BASE + "*"};

	public static String[] getAuthentication() {
		return authentication;
	}

	public static String[] getAgreements() {
        return agreements;
    }

    public static String[] getUi() {
        return ui;
	}
    
    public static String[] getNode() {
        return node;
    }
}
