package org.integratedmodelling.klab.hub;

import java.util.Arrays;

import javax.annotation.PreDestroy;

import org.integratedmodelling.klab.engine.Engine;
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
 * This will start an engine at http://localhost:8283/modeler with the default
 * security config.
 * 
 * @author ferdinando.villa
 * 
 */
@Component
@EnableAutoConfiguration
@ComponentScan(basePackages = { "org.integratedmodelling.klab.engine.rest.security",
		"org.integratedmodelling.klab.engine.rest.controllers.base",
		"org.integratedmodelling.klab.engine.rest.controllers.engine",
		"org.integratedmodelling.klab.engine.rest.controllers.network",
		"org.integratedmodelling.klab.engine.rest.messaging",
		"org.integratedmodelling.klab.engine.rest.controllers.resources" })
public class Hub implements ApplicationListener<ApplicationReadyEvent> { 

	private static Engine engine;
	private static boolean networkServicesStarted = false;
	private ConfigurableApplicationContext context;
	private static Runnable callback;

	@Bean
	public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	@Bean
	public RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
		return new RestTemplate(Arrays.asList(hmc));
	}
	
	private Hub() {
	}

	public Hub(Engine engine) {
		Hub.engine = engine;
	}

	public void stopNetworkServices() {
		if (this.context != null && this.context.isRunning()) {
			this.context.close();
			networkServicesStarted = false;
			this.context = null;
		}
	}
	
	/**
	 * Start network services. Like {@link #startNetworkServices()} but allows a 
	 * callback function to be specified, which is invoked after the network 
	 * services are up.
	 */
	public boolean startNetworkServices(Runnable callback) {
		Hub.callback = callback;
		return startNetworkServices();
	}

	/**
	 * Start network services. Call if constructed with a previously started engine;
	 * won't do anything if run() was called before.
	 */
	public boolean startNetworkServices() {
		if (engine == null) {
			return false;
		}
		if (!networkServicesStarted) {
			this.context = SpringApplication.run(Hub.class, new String[] {});
			networkServicesStarted = true;
		}
		return networkServicesStarted;
	}

	public void run(String[] args) {
		EngineStartupOptions options = new EngineStartupOptions();
		options.initialize(args);
		engine = Engine.start(options);
		SpringApplication.run(Hub.class, options.getArguments());
		networkServicesStarted = true;
	}

	@PreDestroy
	public void shutdown() {
		engine.stop();
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
