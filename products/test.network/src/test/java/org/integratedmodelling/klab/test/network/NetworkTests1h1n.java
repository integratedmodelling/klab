package org.integratedmodelling.klab.test.network;

import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NetworkTests1h1n extends TestNetwork {

	static Engine engine;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
        EngineStartupOptions options = new EngineStartupOptions();
        options.setCertificateResource("testengine.cert");
		start1h1n();
		engine = Engine.start(options);
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
