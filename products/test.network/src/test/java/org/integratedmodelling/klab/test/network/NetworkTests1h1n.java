package org.integratedmodelling.klab.test.network;

import org.integratedmodelling.klab.engine.Engine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NetworkTests1h1n extends TestNetwork {

	static Engine engine;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		start1h1n();
		engine = Engine.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		engine.stop();
		shutdown();
	}

	@Test
	public void test() {
		System.out.println("Hola");
	}

}
