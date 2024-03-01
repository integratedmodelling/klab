package org.integratedmodelling.klab.hub.api;

import java.util.Properties;

public class PropertiesFactory {
	
	private Properties properties;
	
	private PropertiesFactory(MongoNode node, LicenseConfiguration config) {
		this.properties = new NodeProperties(node, config).getProperties(); 
	}
	
	private PropertiesFactory(ProfileResource profile, Agreement agreement, LicenseConfiguration config) {
		this.properties = new EngineProperties(profile, agreement, config).getProperties(); 
	}

	public static PropertiesFactory fromNode(MongoNode node, LicenseConfiguration config) {
		return new PropertiesFactory(node, config);
	}
	
	public static PropertiesFactory fromProfile(ProfileResource profile, Agreement agreement, LicenseConfiguration config) {
		return new PropertiesFactory(profile, agreement, config);
	}

	public Properties getProperties() {
		return properties;
	}

}
