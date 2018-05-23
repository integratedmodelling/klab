package org.integratedmodelling.klab.engines.modeler.base;

import javax.annotation.PreDestroy;

import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

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
public class Modeler implements ApplicationListener<ApplicationReadyEvent> { 

	private static Engine engine;
	private static boolean networkServicesStarted = false;
	private ConfigurableApplicationContext context;
	private static Runnable callback;

	private Modeler() {
	}

	public Modeler(Engine engine) {
		Modeler.engine = engine;
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
	public void startNetworkServices(Runnable callback) {
		Modeler.callback = callback;
		startNetworkServices();
	}

	/**
	 * Start network services. Call if constructed with a previously started engine;
	 * won't do anything if run() was called before.
	 */
	public void startNetworkServices() {
		if (engine == null) {
			throw new KlabInternalErrorException("engine is null: cannot start network services");
		}
		if (!networkServicesStarted) {
			this.context = SpringApplication.run(Modeler.class, new String[] {});
			networkServicesStarted = true;
		}
	}

	public void run(String[] args) {
		EngineStartupOptions options = new EngineStartupOptions();
		options.initialize(args);
		engine = Engine.start(options);
		SpringApplication.run(Modeler.class, options.getArguments());
		networkServicesStarted = true;
	}

	@PreDestroy
	public void shutdown() {
		engine.stop();
	}

	public static void main(String args[]) {
		new Modeler().run(args);
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		if (callback != null) {
			callback.run();
		}
	}

}
