package org.integratedmodelling.klab.test.network;

import org.integratedmodelling.klab.engine.Engine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LocalResourceTests {

	static Engine engine;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		engine = SetupResources.startAndLoad(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		engine.stop();
	}

	@Test
	public void testWcs1() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim.data.local/test1.kim")).get();
	}

}
