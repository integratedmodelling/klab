package org.integratedmodelling.klab.test.network;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.hub.Hub;
import org.integratedmodelling.klab.node.Node;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Sets up various test network configurations on the local machine. Run as an
 * application to make the configuration active for testing outside of JUnit.
 * 
 * @author ferdinando.villa
 *
 */
public class TestNetwork {

	static Map<String, Hub> hubs = new HashMap<>();
	static Map<String, Node> nodes = new HashMap<>();
	static Map<String, Engine> engines = new HashMap<>();
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

//		Map<String, Object> hprops = new HashMap<>();
//		hprops.put("server.port", "8284");
//		hprops.put("server.servlet.contextPath", "/klab");
//
//		SpringApplication hub = new SpringApplicationBuilder(Hub.class)
//				.properties("SOA.ControllerFactory.enforceProxyCreation=true").build();
//		hub.setDefaultProperties(hprops);
//		ConfigurableApplicationContext hcontext = hub.run();
//
//		Map<String, Object> nprops = new HashMap<>();
//		nprops.put("server.port", "8287");
//		nprops.put("server.servlet.contextPath", "/node");

		hubs.put("hub", startHub(8284));

//		SpringApplication node = new SpringApplicationBuilder(Node.class)
//				.properties("SOA.ControllerFactory.enforceProxyCreation=true").build();
//		node.setDefaultProperties(nprops);
//		ConfigurableApplicationContext ncontext = node.run();

		nodes.put("node", Node.start());
	}

	public static Hub startHub(int port) {
		return Hub.start();
	}

	public static Node startNode(Hub hub, int port) {
		return Node.start();
	}
	
	public static Engine startEngine(Hub hub, int port) {
		return Engine.start();
	}
	
	/**
	 * Shut down whatever configuration was started.
	 */
	public static void shutdown() {
		
		for (Engine engine : engines.values()) {
			engine.stop();
		}
		
		for (Node node : nodes.values()) {
			node.stop();
		}
		
		for (Hub hub : hubs.values()) {
			hub.stop();
		}
		
		nodes.clear();
		hubs.clear();
		started = false;
	}
	
	/**
	 * Run the desired configuration, defaulting at 1h1n.
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		String configuration = "1h1n";
		if (args != null && args.length > 0) {
			configuration = args[0];
		}
		switch (configuration) {
		case "1h1n": 
			start1h1n(); 
			break;
		}
		
		while (true) {
			Thread.sleep(1000);
		}
	}

}
