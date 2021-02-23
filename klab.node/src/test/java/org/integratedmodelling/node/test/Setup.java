package org.integratedmodelling.node.test;

import org.integratedmodelling.klab.node.Node;
import org.integratedmodelling.klab.node.NodeStartupOptions;

public enum Setup {
	
	INSTANCE;

	/**
	 * Start a test node on the passed port authenticating with the passed hub.
	 * 
	 * @param hub
	 * @param port
	 * @return the active node
	 */
	public Node startNode(int port) {
		return Node.start(new NodeStartupOptions("-certResource", "testnode.cert", /* "-hub", hub.getLocalAddress(), */
				"-port", "" + port));
	}

	public static void main(String[] args) throws InterruptedException {
		String configuration = "1n";
		if (args != null && args.length > 0) {
			configuration = args[0];
		}
		switch (configuration) {
		case "1n":
			INSTANCE.startNode(8287);
			break;
		case "2n":
			INSTANCE.startNode(8287);
			INSTANCE.startNode(8288);
			break;
		}

		while (true) {
			Thread.sleep(1000);
		}
	}
	
}
