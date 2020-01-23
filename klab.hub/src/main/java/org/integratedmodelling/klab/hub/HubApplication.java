package org.integratedmodelling.klab.hub;

import javax.annotation.PreDestroy;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * This will start a hub at http://localhost:8284/klab with the default security
 * config.
 * 
 * @author ferdinando.villa
 * 
 */
@Component
@PropertySource("classpath:default.properties")
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ComponentScan(basePackages = { 
		"org.integratedmodelling.klab.hub.security",
		"org.integratedmodelling.klab.hub.services",
		"org.integratedmodelling.klab.hub.authentication",
		"org.integratedmodelling.klab.hub.network",
		"org.integratedmodelling.klab.hub.controllers",
		"org.integratedmodelling.klab.hub.config",
		"org.integratedmodelling.klab.hub.repository",
		"org.integratedmodelling.klab.hub.models",
		"org.integratedmodelling.klab.hub.manager",
		"org.integratedmodelling.klab.hub.tasks"
		})

public class HubApplication {

	private static Hub hub;
	
	public void run(String[] args) {
		HubStartupOptions options = new HubStartupOptions();
		options.initialize(args);
		hub = Hub.start(options);
	}

	@PreDestroy
	public void shutdown() {
		hub.stop();
	}

	public static void main(String args[]) {
		new HubApplication().run(args);
	}

}
