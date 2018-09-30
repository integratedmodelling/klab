package org.integratedmodelling.klab.test.network;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.hub.Hub;
import org.integratedmodelling.klab.hub.HubStartupOptions;

/**
 * Sets up various test network configurations on the local machine. Run as an
 * application to make the configuration active for testing outside of JUnit.
 * 
 * @author ferdinando.villa
 *
 */
public enum SetupNetwork {

	INSTANCE;

	private Map<String, Hub> hubs = new HashMap<>();
	private Map<String, Engine> engines = new HashMap<>();
	private boolean started = false;

	/**
	 * Start a test network with one hub (on localhost:8284/klab) and one node (on
	 * localhost:8287/node). The hub will authenticate anything local (including an
	 * anonymous certificate) and provide a network with the node in it.
	 */
	public void start1hub() {

		if (started) {
			throw new IllegalStateException("cannot start more than one configuration at the same time.");
		}
		started = true;
		Hub hub = startHub(8284);
		hubs.put("hub", hub);
	}

	/**
	 * Start a test hub on the passed port and return it.
	 * 
	 * @param port
	 * @return the active hub
	 */
	public Hub startHub(int port) {
		return Hub.start(new HubStartupOptions("-certResource", "testhub.cert", "-port", "" + port));
	}

	/**
	 * Shut down whatever configuration was started.
	 */
	public void shutdown() {

		for (Engine engine : engines.values()) {
			engine.stop();
		}

		for (Hub hub : hubs.values()) {
			hub.stop();
		}

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
			INSTANCE.start1hub();
			break;
		}

		while (true) {
			Thread.sleep(1000);
		}
	}

}
