package org.integratedmodelling.klab.hub.license;

import java.util.Properties;

import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.users.User;

public class PropertiesFactory {
	
	private Properties properties;
	
	private PropertiesFactory(MongoNode node, LicenseConfiguration config) {
		this.properties = new NodeProperties(node, config).getProperties(); 
	}
	
	private PropertiesFactory(User user, LicenseConfiguration config) {
		this.properties = new EngineProperties(user, config).getProperties(); 
	}

	public static PropertiesFactory fromNode(MongoNode node, LicenseConfiguration config) {
		return new PropertiesFactory(node, config);
	}
	
	public PropertiesFactory fromUser(User user, LicenseConfiguration config) {
		return new PropertiesFactory(user, config);
	}

	public Properties getProperties() {
		return properties;
	}

}
