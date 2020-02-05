package org.integratedmodelling.klab.hub.listeners;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.repository.DockerConfigurationRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class NetworkDockerStartupEvent {
	
	public NetworkDockerStartupEvent(DockerConfigurationRepository repo) {
		super();
		
	}
	@EventListener
	public void startup(ApplicationReadyEvent event) {
		Logging.INSTANCE.info("---------Starting Network Docker Startup---------\n");
	}

}
