package org.integratedmodelling.klab.explorer;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.panda_lang.pandomium.Pandomium;
import org.panda_lang.pandomium.PandomiumConstants;
import org.panda_lang.pandomium.settings.PandomiumSettings;
import org.panda_lang.pandomium.util.os.PandomiumOSType;
import org.panda_lang.pandomium.wrapper.PandomiumBrowser;
import org.panda_lang.pandomium.wrapper.PandomiumClient;
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
public class Main implements ApplicationListener<ApplicationReadyEvent> {

	private static Engine engine;
	private static boolean networkServicesStarted = false;
	private ConfigurableApplicationContext context;
	private static Runnable callback;
	private static ISession session;

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

	private Main() {
	}

	public Main(Engine engine) {
		Main.engine = engine;
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
		Main.callback = callback;
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
			props.put("server.servlet.contextPath", contextPath);
			SpringApplication app = new SpringApplication(Main.class);
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

		engine = Engine.start(options);
		session = engine.createSession().setDefault();
		System.out.println("DEFAULT SESSION is " + session.getId());

		SpringApplication app = new SpringApplication(Main.class);
		app.setDefaultProperties(props);
		this.context = app.run(options.getArguments());

		PandomiumSettings settings = PandomiumSettings.builder()
				.dependencyURL(PandomiumOSType.OS_WINDOWS,
						PandomiumConstants.Repository.NATIVES_URL + "win64-natives.tar.xz")
				.dependencyURL(PandomiumOSType.OS_MAC,
						PandomiumConstants.Repository.NATIVES_URL + "mac64-natives.tar.xz")
				.dependencyURL(PandomiumOSType.OS_LINUX,
						PandomiumConstants.Repository.NATIVES_URL + "linux64-natives.tar.xz")
				.nativeDirectory("natives").loadAsync(false).build();

		Pandomium pandomium = new Pandomium(settings);
		pandomium.initialize();

		JFrame frame = new JFrame();

		PandomiumClient client = pandomium.createClient();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});

		PandomiumBrowser browser = client.loadURL("http://localhost:8283/modeler/ui/viewer?session=" + session.getId() + "&mode=ide");

		frame.getContentPane().add(browser.toAWTComponent(), BorderLayout.CENTER);

		frame.setTitle("k.LAB Explorer");
		frame.setSize(1720, 840);
		frame.setVisible(true);

		networkServicesStarted = true;
	}

	@PreDestroy
	public void shutdown() {
		engine.stop();
	}

	public static void main(String args[]) {
		new Main().run(args);
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		if (callback != null) {
			callback.run();
		}
	}

}
