package org.integratedmodelling.klab.engine.rest.security;

import org.integratedmodelling.klab.api.API;

public class EngineRequestMatchers {

	
    private static final String[] engine = new String[]{"/engine/project/resource/get/**", API.ENGINE.STATUS, "/engine/session/view/displaydata/**"};

    private static final String[] capabilities = new String[]{API.CAPABILITIES, API.KIM.CAPABILITIES, API.KACTORS.CAPABILITIES};
    
    private static final String[] template = new String[]{API.KIM.TEMPLATE, API.KACTORS.TEMPLATE};
    
    private static final String[] schema = new String[]{API.SCHEMA + "*"};
    
    private static final String[] ping = new String[]{API.PING + "*"};
    
    private static final String[] ui = new String[]{API.ENGINE.UI + "*"};
    

	public static String[] getEngine() {
		return engine;
	}

	public static String[] getCapabilities() {
		return capabilities;
	}

	public static String[] getTemplate() {
		return template;
	}

	public static String[] getSchema() {
		return schema;
	}

	public static String[] getPing() {
		return ping;
	}

	public static String[] getUi() {
		return ui;
	}
	

}
