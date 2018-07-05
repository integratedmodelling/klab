package org.integratedmodelling.klab.hub;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This will start a hub at http://localhost:8284/klab with the default security
 * config.
 * 
 * @author ferdinando.villa
 * 
 */
@Component
@EnableAutoConfiguration
@ComponentScan(basePackages = { 
		"org.integratedmodelling.klab.hub.security",
		"org.integratedmodelling.klab.hub.authentication",
		"org.integratedmodelling.klab.hub.network" })
public class Hub implements ApplicationListener<ApplicationReadyEvent> {

	private static Runnable callback;
	private ConfigurableApplicationContext context;

	// defaults
	private static int port = 8284;
	private static String contextPath = "/klab";

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
		props.put("server.port", "" + port);
		props.put("server.servlet.contextPath", contextPath);
		SpringApplication app = new SpringApplication(Hub.class);
		app.setDefaultProperties(props);
		this.context = app.run(options.getArguments());

	}

	@PreDestroy
	public void shutdown() {
	}

	public static void main(String args[]) {
		new Hub().run(args);
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		if (callback != null) {
			callback.run();
		}
	}

}
