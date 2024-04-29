package org.integratedmodelling.klab.hub.licenses.dto;

import java.util.Properties;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
import org.integratedmodelling.klab.hub.nodes.dtos.NodeProperties;
import org.integratedmodelling.klab.hub.users.dto.ProfileResource;

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
