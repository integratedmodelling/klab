package org.integratedmodelling.klab.hub.listeners;

import org.integratedmodelling.klab.hub.network.DockerConfiguration;
import org.integratedmodelling.klab.hub.repository.DockerConfigurationRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NetworkDockerStartupEvent {
	
	public NetworkDockerStartupEvent(DockerConfigurationRepository repo) {
		super();
		this.repository = repo;
		
	}
	private DockerConfigurationRepository repository;
	
	@EventListener
	public void startup(ContextRefreshedEvent event) {
		DockerConfiguration config = new DockerConfiguration();
		config.setApiVersion("1.39");
		config.setDockerHost("tcp://127.0.0.1:2375");
		repository.insert(config);
	}

}
