package org.integratedmodelling.klab.hub.payload;

import java.util.Properties;

import javax.validation.constraints.NotBlank;

import org.integratedmodelling.klab.hub.api.MongoNode;

public class DockerNodeConfigurationRequest {
	@NotBlank
	private MongoNode node;
	
	private Properties properties;

	public MongoNode getNode() {
		return node;
	}

	public void setNode(MongoNode node) {
		this.node = node;
	}
	
}
