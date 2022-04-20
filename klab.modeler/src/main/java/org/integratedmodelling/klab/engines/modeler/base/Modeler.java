package org.integratedmodelling.klab.engines.modeler.base;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.services.IConfigurationService;
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
public class Modeler implements ApplicationListener<ApplicationReadyEvent> {

	private static Engine engine;
	private static boolean networkServicesStarted = false;
	private ConfigurableApplicationContext context;
	private static Runnable callback;

	// defaults
	private static int port = 8283;
	private static String contextPath = "/modeler";

	@Bean
	public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	@Bean
	public RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
		return new RestTemplate(Arrays.asList(hmc));
	}

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
	public boolean startNetworkServices(Runnable callback) {
		Modeler.callback = callback;
		return startNetworkServices();
	}

	/**
	 * Start network services. In the engine this is optional. Call if constructed
	 * with a previously started engine; won't do anything if run() was called
	 * before.
	 */
	public boolean startNetworkServices() {
		if (engine == null) {
			return false;
		}
		if (!networkServicesStarted) {
			Map<String, Object> props = new HashMap<>();
			props.put("server.port", "" + port);
			props.put("spring.main.banner-mode", "off");
			props.put("logging.file", Configuration.INSTANCE.getProperty(IConfigurationService.KLAB_LOG_FILE,
					Configuration.INSTANCE.getDataPath("logs") + File.separator + "klab.log"));
			props.put("server.servlet.contextPath", contextPath);
			props.put("spring.servlet.multipart.max-file-size", "1024MB"); //
			props.put("spring.servlet.multipart.max-request-size", "1024MB");
			SpringApplication app = new SpringApplication(Modeler.class);
			app.setDefaultProperties(props);
			this.context = app.run();
			networkServicesStarted = true;
		}
		return networkServicesStarted;
	}

	public void run(String[] args) {
		EngineStartupOptions options = new EngineStartupOptions();
		options.initialize(args);
		Map<String, Object> props = new HashMap<>();
		props.put("server.port", "" + port);
		props.put("spring.main.banner-mode", "off");
		props.put("server.servlet.contextPath", contextPath);
		props.put("logging.file", Configuration.INSTANCE.getProperty(IConfigurationService.KLAB_LOG_FILE,
				Configuration.INSTANCE.getDataPath("logs") + File.separator + "klab.log"));
		engine = Engine.start(options);
		SpringApplication app = new SpringApplication(Modeler.class);
		app.setDefaultProperties(props);
		this.context = app.run(options.getArguments());
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
