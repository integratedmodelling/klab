package org.integratedmodelling.klab.node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.inject.Singleton;

import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This will start a node at http://localhost:8287/node with the default
 * security config.
 * 
 * @author ferdinando.villa
 * 
 */
@Component
@Singleton
@EnableAutoConfiguration
@ComponentScan(basePackages = { "org.integratedmodelling.klab.engine.rest.controllers.base" })
public class Node implements ApplicationListener<ApplicationStartingEvent> {

	private ConfigurableApplicationContext context;
	
//	@Autowired
	
	
	// defaults
	private static int port = 8287;
	private static String contextPath = "/node";

	@Bean
	public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	@Bean
	public RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
		return new RestTemplate(Arrays.asList(hmc));
	}

	public void run(String[] args) {
		EngineStartupOptions options = new EngineStartupOptions();
		options.initialize(args);
		Map<String, Object> props = new HashMap<>();
		props.put("server.port", ""+port);
		props.put("server.servlet.contextPath", contextPath);
		SpringApplication app = new SpringApplication(Node.class);
		app.setDefaultProperties(props);
		this.context = app.run(options.getArguments());
	}

	@PreDestroy
	public void shutdown() {
		// TODO engine shutdown if needed
	}

	public static void main(String args[]) {
		new Node().run(args);
	}

	@Override
	public void onApplicationEvent(ApplicationStartingEvent arg0) {
	}

}
