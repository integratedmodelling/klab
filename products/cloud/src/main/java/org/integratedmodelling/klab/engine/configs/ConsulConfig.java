package org.integratedmodelling.klab.engine.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/*
 * The class if for retrieving the properties needed to configure the 
 */
@Configuration
public class ConsulConfig {

	@Value("${spring.cloud.consul.host}")
	private String host;

	@Value("${spring.cloud.consul.port}")
	private String port;
	
	@Value("${spring.cloud.consul.discovery.instanceId}")
	private String id;
	
	public String getHost() {
		return host;
	}

	public String getId() {
		return id;
	}

	public String getServiceUrl() {
		return "http://" + host + ":" + port + "/v1/agent/service/" + getId();
	}
	
	public String registerServiceUrl() {
		return "http://" + host + ":" + port + "/v1/agent/service/register";
	}
	
}
