package org.integratedmodelling.klab.test.network;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.hub.Hub;
import org.integratedmodelling.klab.node.Node;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Sets up various test network configurations on the local machine.
 * 
 * @author ferdinando.villa
 *
 */
public class TestNetwork {

	static Map<String, ConfigurableApplicationContext> hubs = new HashMap<>();
	static Map<String, ConfigurableApplicationContext> nodes = new HashMap<>();
	static boolean started = false;

	/**
	 * Start a test network with one hub (on localhost:8284/klab) and one node (on
	 * localhost:8287/node). The hub will authenticate anything (including an
	 * anonymous certificate) and provide a network with the node in it.
	 */
	public static void start1h1n() {

		if (started) {
			throw new IllegalStateException("cannot start more than one configuration at the same time.");
		}
		started = true;

		Map<String, Object> hprops = new HashMap<>();
		hprops.put("server.port", "8284");
		hprops.put("server.servlet.contextPath", "/klab");

		SpringApplication uws = new SpringApplicationBuilder(Hub.class)
				.properties("SOA.ControllerFactory.enforceProxyCreation=true").build();
		uws.setDefaultProperties(hprops);
		ConfigurableApplicationContext hcontext = uws.run();

		Map<String, Object> nprops = new HashMap<>();
		nprops.put("server.port", "8287");
		nprops.put("server.servlet.contextPath", "/node");

		hubs.put("hub", hcontext);
		
		SpringApplication pws = new SpringApplicationBuilder(Node.class)
				.properties("SOA.ControllerFactory.enforceProxyCreation=true").build();
		pws.setDefaultProperties(nprops);
		ConfigurableApplicationContext ncontext = pws.run();
		
		nodes.put("node", ncontext);
	}

	/**
	 * Shut down whatever configuration was started.
	 */
	public static void shutdown() {
		for (ConfigurableApplicationContext node : nodes.values()) {
			node.close();
		}
		for (ConfigurableApplicationContext hub : hubs.values()) {
			hub.close();
		}
		nodes.clear();
		hubs.clear();
		started = false;
	}


}
